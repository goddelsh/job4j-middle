package di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartUI {


    private Store store;

    private ConsoleInput consoleInput;

//    public StartUI(Store store, ConsoleInput consoleInput) {
//        this.store = store;
//        this.consoleInput = consoleInput;
//    }

    public void ask() {
        store.add(consoleInput.askStr("Input string: "));
    }

    public void add(String value) {
        store.add(value);
    }

    public void print() {
        for (String value : store.getAll()) {
            System.out.println(value);
        }
    }

    public Store getStore() {
        return store;
    }
    @Autowired
    public void setStore(Store store) {
        this.store = store;
    }

    public ConsoleInput getConsoleInput() {
        return consoleInput;
    }
    @Autowired
    public void setConsoleInput(ConsoleInput consoleInput) {
        this.consoleInput = consoleInput;
    }
}