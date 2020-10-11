package nba;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NBACache {
    final private Map<Integer, Base> versionMap = new ConcurrentHashMap<>();

    public void add(Base model) {
        Base changingModel = new Base(model.getId(), model.getVersion(), model.getName());
        Base previosValue = this.versionMap.putIfAbsent(model.getId(), changingModel);
        if (previosValue != null) {
            if (previosValue.getVersion() == changingModel.getVersion()) {
                changingModel.incVersion();
                this.versionMap.put(model.getId(), changingModel);
            } else {
                throw new OptimisticException();
            }
        }
    }

    public void update(Base model) {
        Base changingModel = new Base(model.getId(), model.getVersion(), model.getName());
        this.versionMap.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() == changingModel.getVersion()) {
                changingModel.incVersion();
                return changingModel;
            } else {
                throw new OptimisticException();
            }
        });
    }

    public void delete(Base model) {
        Base elem = this.get(model);
        if (elem.getVersion() == model.getVersion()) {
            this.versionMap.remove(elem.getId());
        } else {
            throw new OptimisticException();
        }
    }

    public Base get(Base model) {
        Base result = this.versionMap.getOrDefault(model.getId(), null);
        if (result != null) {
            result = new Base(result.getId(), result.getVersion(), result.getName());
        }
        return result;
    }
}
