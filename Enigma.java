public class Enigma{

    private String rotorInit[] = {
        "#GNUAHOVBIPWCJQXDKRYELSZFMT",
        "#EJOTYCHMRWAFKPUZDINSXBGLQV",
        "#BDFHJLNPRTVXZACEGIKMOQSUWY",
        "#NWDKHGXZVRIFJBLMAOPSCYUTQE",
        "#TGOWHLIFMCSZYRVXQABUPEJKND"
    };

    // rotors[0] = inner (fast), rotors[1] = middle, rotors[2] = outer (fixed)
    private Rotor rotors[];

    public Enigma(int id1, int id2, int id3, String start){
        rotors = new Rotor[3];
        rotors[0] = new Rotor(rotorInit[id1-1], start.charAt(0));
        rotors[1] = new Rotor(rotorInit[id2-1], start.charAt(1));
        rotors[2] = new Rotor(rotorInit[id3-1], start.charAt(2)); // fixed ring after init
    }

    // --- decrypt first ---
    public String decrypt(String message){
        StringBuilder out = new StringBuilder(message.length());
        for (int k = 0; k < message.length(); k++) {
            char c = message.charAt(k);

            int i2 = rotors[2].indexOf(c);          // outer -> index
            char mChar = rotors[1].charAt(i2);      // aligned on middle

            int i1 = rotors[2].indexOf(mChar);      // outer -> index
            char p  = rotors[0].charAt(i1);         // aligned on inner

            out.append(p);

            rotate(); // step after each char
        }
        return out.toString();
    }

    public String encrypt(String message){
        StringBuilder out = new StringBuilder(message.length());
        for (int k = 0; k < message.length(); k++) {
            char p = message.charAt(k);

            int i1 = rotors[0].indexOf(p);          // inner -> index
            char c1 = rotors[2].charAt(i1);         // aligned on outer

            int i2 = rotors[1].indexOf(c1);         // middle -> index
            char c  = rotors[2].charAt(i2);         // aligned on outer

            out.append(c);

            rotate(); // step after each char
        }
        return out.toString();
    }

    // Step inner every char; when inner wraps, step middle once; outer never steps
    private void rotate(){
        boolean wrapped = rotors[0].rotate();
        if (wrapped) {
            rotors[1].rotate();
        }
    }
}