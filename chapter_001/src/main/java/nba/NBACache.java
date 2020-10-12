package nba;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NBACache {
    final private Map<Integer, Base> versionMap = new ConcurrentHashMap<>();

    public void add(Base model) {
        Base changingModel = new Base(model.getId(), model.getVersion(), model.getName());
        if (this.versionMap.putIfAbsent(model.getId(), changingModel) != null) {
                throw new OptimisticException();
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

        this.versionMap.computeIfPresent(model.getId(), (key, value) -> {
            if (value.getVersion() == model.getVersion()) {
                return null;
            } else {
                throw new OptimisticException();
            }
        });
    }

    public Base get(Base model) {
        Base result = this.versionMap.getOrDefault(model.getId(), null);
        if (result != null) {
            result = new Base(result.getId(), result.getVersion(), result.getName());
        }
        return result;
    }
}
