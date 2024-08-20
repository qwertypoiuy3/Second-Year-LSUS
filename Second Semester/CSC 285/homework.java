import java.util.Arrays;
import java.util.Random;

public class Signals {
    public static void main(String []args){
        DigitalSource d = new DigitalSource();
        Client c = new Client(d);
        c.processSignal(20);
        
        AnalogSource a = new AnalogSource();
        AnalogSourceAdapter x = new AnalogSourceAdapter(a);
        c = new Client(x);
        c.processSignal(20);

        MorseSource b = new MorseSource();
        MorseSourceAdapter y = new MorseSourceAdapter(b);
        c = new Client(y);
        c.processSignal(20);
    }
}

class Client {
    private DigitalSource d;
    public Client(DigitalSource d) {
        this.d = d;
    }
    public void processSignal(int n) {
        int[] signal = d.getSignal(n);
        System.out.println(Arrays.toString(signal));
    }
}

class DigitalSource {
    public int[] getSignal(int n) {
        int[] signal = new int[n];
        Random random = new Random(n);
        for (int i = 0; i < n; ++i) {
            signal[i] = random.nextBoolean() ? 1 : 0;
        }
        return signal;
    }
}

class AnalogSource {
    public float[] getSignal(int n) {
        float[] signal = new float[n];
        Random random = new Random(n);
        for (int i = 0; i < n; ++i) {
            signal[i] = random.nextFloat();
        }
        return signal;
    }
}

class AnalogSourceAdapter extends DigitalSource {
    private AnalogSource src;
    public AnalogSourceAdapter(AnalogSource src) {this.src = src;}
    public void processSignal(int n)
    {
        src.getSignal(n);
    }
    public int[] getSignal(int n)
    {
        float[] decimalList = src.getSignal(n);
        int[] integerList = new int[n];
        for(int i = 0; i < n; i++)
        {
            if(decimalList[i] > 0.5)
            {
                integerList[i] = 1;
            }
            else if(decimalList[i] < 0.5)
            {
                integerList[i] = 0;
            }
        }
        return integerList;
    }
}

enum MCode {
    DASH, DOT, SPACE
}

class MorseSource {
    public MCode[] getSignal(int n) {
        MCode[] signal = new MCode[n];
        Random random = new Random(n);
        for (int i = 0; i < n; ++i) {
            int s = random.nextInt(3);
            switch (s) {
                case 0:
                signal[i] = MCode.DASH;
                break;
                case 1:
                signal[i] = MCode.DOT;
                break;
                case 2:
                signal[i] = MCode.SPACE;
                break;
            }
        }
        return signal;
    }
}

class MorseSourceAdapter extends DigitalSource {
    private MorseSource src;
    public MorseSourceAdapter(MorseSource src) {this.src = src;}
    public void processSignal(int n)
    {
        src.getSignal(n);
    }
    public int[] getSignal(int n)
    {
        MCode[] morseList = src.getSignal(n);
        int[] integerList = new int[n];
        for(int i = 0; i < n; i++)
        {
            if(morseList[i] == MCode.DASH || morseList[i] == MCode.DOT)
            {
                integerList[i] = 0;
            }
            else if(morseList[i] == MCode.SPACE)
            {
                integerList[i] = 1;
            }
        }
        return integerList;
    }
}
