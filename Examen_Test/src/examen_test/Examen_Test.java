/**
 *
 * @author Fahym Abd Elfattah
 */
package examen_test;

import java.io.UnsupportedEncodingException;
import javax.smartcardio.*;

public class Examen_Test {
        
    //0x00 0xA4 0x04 0x00 0X06 AID <0256EC7F4819> ;
    private static byte[] SELECT_AID ={(byte)0x00,(byte)0xA4,0x04,0x00,0X06,0X02,0X56,(byte)0XEC,(byte)0X7F,(byte)0X48,(byte)0X19 };
    
    private static byte[] INS_VERIF_PIN ={(byte)0x80,0x20,0x00,0x00,0x04,0x01,0x01,0x01,0x01};
    private static byte[] INS_VERIF_ADM ={(byte)0x80,0x20,0x01,0x00,0x06,0x53,0x45,0x53,0x4E,0x75,0x6D};
    
    private static byte[] INS_DEBLOQUE_PIN ={(byte)0x80,0x21,0x00,0x00,0x00};
    private static byte[] INS_RESET_ADM_PIN ={(byte)0x80,0x23,0x00,0x00,0x00};
    private static byte[] INS_MAJ_PIN ={(byte)0x80,0x22,0x00,0x00,0x04,0x01,0x01,0x01,0x02};
    
    private static byte[] INS_WRITE_NF ={(byte)0x80,(byte)0xB2,0x01,0x00,0x01,0x04};
    private static byte[] INS_WRITE_PF ={(byte)0x80,(byte)0xB2,0x02,0x00,0x04,(byte)0xEE,(byte)0xDD,(byte)0xCC,(byte)0xFF};
   
    private static byte[] INS_READ_NF ={(byte)0x80,(byte)0xD2,0x01,0x00,0x00};
    private static byte[] INS_READ_PF ={(byte)0x80,(byte)0xD2,0x02,0x00,0x00};

    public static void main(String[] args)throws CardException ,UnsupportedEncodingException {
        Commande cmd = new Commande();
        
        TerminalFactory tf=TerminalFactory.getDefault();
        CardTerminals lecteurs = tf.terminals();
        CardTerminal lecteur = lecteurs.getTerminal("Gemalto Prox-DU Contact_12400200 0");
        Card card=null ;
        System.out.println("Attente de la carte ");
        if(lecteur.isCardPresent()){
            card=lecteur.connect("*");
            System.out.println("Terminal connected");
            if(card!=null){
                System.out.println("Protocol de la carte: "+ card.getProtocol());
                CardChannel ch = card.getBasicChannel();
                //_________________Send APDU Commande___________________________
                cmd.sendCommande(card, SELECT_AID, "Ok: Selection du l'aplet ", "Erreure: selection de l'aplet ");
                
                cmd.sendCommande(card, INS_VERIF_PIN, "Ok: Vérification du code PIN ", "Erreure: code PIN erroné ou  bloqué ");
                cmd.sendCommande(card, INS_VERIF_ADM, "Ok: Vérification du Code Administrateur ", "Erreure: Code Administrateur erroné ou  bloqué ");
                
                cmd.sendCommande(card, INS_DEBLOQUE_PIN, "Ok: Code PIN Débloquer ", "Erreure: Code PIN N'est pas débloquer");
                cmd.sendCommande(card, INS_RESET_ADM_PIN, "Ok: Code PIN & Administrateur réinitialiser ", "Erreure: Code PIN & Administrateur N'est pas réinitialiser");
                cmd.sendCommande(card, INS_MAJ_PIN, "Ok: Mise à jour du code PIN ", "Erreure: Mise à jour du code PIN");
                
                cmd.sendCommande(card, INS_WRITE_NF, "Ok: Ecrire dans NBRE_FILE", "Erreure: Ecrire dans NBRE_FILE");
                cmd.sendCommande(card, INS_READ_NF, "Ok: Lecture depuis NBRE_FILE", "Erreure: Lecture depuis NBRE_FILE");
                
                cmd.sendCommande(card, INS_WRITE_PF, "Ok: Ecrire dans PEC_FILE", "Erreure: Ecrire dans PEC_FILE");
                cmd.sendCommande(card, INS_READ_PF, "Ok: Lecture depuis PEC_FILE", "Erreure: Lecture depuis PEC_FILE");
                card.disconnect(true);           
            }        
        }
    } 
}
