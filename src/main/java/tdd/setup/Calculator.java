package tdd.setup;

// behaviour inspired by https://www.online-calculator.com/
public class Calculator {

    private String screen = "0";

    private double latestValue;

    private String latestOperation = "";

    public String readScreen() {
        return screen;
    }
    public void pressDigitKey(int digit) {
        if(digit > 9 || digit < 0) throw new IllegalArgumentException();

        if(latestOperation.isEmpty()) {
            screen = screen + digit;
        } else if (latestValue == 0.0){
            latestValue = Double.parseDouble(screen);
            screen = Integer.toString(digit);
        }
        else{
            screen = screen + digit;
        }
    }

    public void pressClearKey() {
        screen = "0";
        latestOperation = "";
        latestValue = 0.0;
    }

    public void pressOperationKey(String operation)  {
        if (operation != "%") {
            latestOperation = operation;
        }
        else {
            var result = Double.parseDouble(screen) / 100.0;
            screen = Double.toString(result);
            if(screen.endsWith(".0")) screen = screen.substring(0,screen.length()-2);
        }
    }

    public void pressDotKey() {
        if(!screen.endsWith(".")) screen = screen + ".";
    }

    public void pressNegative() {
        screen = screen.startsWith("-") ? screen.substring(1) : "-" + screen;
    }

    public void pressEquals() {
        var result = switch(latestOperation) {
            case "+" -> latestValue + Double.parseDouble(screen);
            case "-" -> latestValue - Double.parseDouble(screen);
            case "x" -> latestValue * Double.parseDouble(screen);
            case "/" -> latestValue / Double.parseDouble(screen);
            default -> throw new IllegalArgumentException();
        };
        screen = Double.toString(result);
        if(screen.endsWith(".0")) screen = screen.substring(0,screen.length()-2);
        if(screen.contains(".0E-")) screen = screen.replace(".0E","e");
        if(screen.contains(".0E")) screen = screen.replace(".0E","e+");
    }
}
