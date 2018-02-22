package org.laki.cardreder;

import javax.smartcardio.*;
import javax.xml.bind.DatatypeConverter;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = null;
        try {
            terminals = factory.terminals().list();
            System.out.println("Terminals: " + terminals);
        } catch (CardException e) {
            e.printStackTrace();
        }
        CardTerminal terminal = terminals.get(0);
        // establish a connection with the card
        Card card=null;
        try {
             card = terminal.connect("*");
            System.out.println("card: " + card);
        } catch (CardException e) {
            e.printStackTrace();
            System.out.println(e.getCause());
        }
        byte[] apduCommand=new byte[]{(byte)0x00,(byte)0xA4,(byte)0x04,(byte) 0x0C,(byte) 0x6,(byte)0xD2,(byte)0x76,
                (byte)0x00,(byte)0x00,(byte)0x01,(byte)0x02};

        String apdu="00b08200000255";
        String redaApdu="00A4040006D27600000101";
        CardChannel channel = card.getBasicChannel();
        CommandAPDU command=new CommandAPDU(apduCommand);

       ResponseAPDU r = null;
        try {
            r = channel.transmit(command);

        } catch (CardException e) {
            e.printStackTrace();
            System.out.println("Command didnt work" +e.getCause());
        }

        
//        System.out.println("response: " + (r.getBytes().toString()));
        // disconnect


        try {
            card.disconnect(false);

        } catch (CardException e) {
            e.printStackTrace();
        }


        // System.out.println("Hello World!");
    }

    private static byte[] getByteBuff(String apdu) {
        return DatatypeConverter.parseHexBinary(apdu);
    }
}
