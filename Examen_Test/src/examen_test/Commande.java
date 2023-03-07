
/**
 *
 * @author Fahym Abd Elfattah
 */ 
package examen_test;
import java.util.List;
import javax.smartcardio.CardException;
import javax.smartcardio.*;

public class Commande {
    public Commande(){
        
    }
    public static void sendCommande(Card card,byte[] cmdCode,String msg,String erreur)throws CardException,UnsupportedOperationException{
        CardChannel ch = card.getBasicChannel();
        ResponseAPDU rp = ch.transmit(new CommandAPDU(cmdCode));
        if (rp.getSW()==0x9000)
            System.out.println(msg);
        else
            System.out.println(erreur + Integer.toHexString(rp.getSW()) );
    }
}
