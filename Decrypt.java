import java.io.*;
import java.security.*;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.text.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

class FileContent implements Serializable{
    String magicString = "NW";
    byte[] label = new byte[100];
    byte[] message = new byte[1000];
}

public class Decrypt extends javax.swing.JFrame implements ActionListener{

       public Decrypt() {
        super("No Whisper");
        initComponents();
    }

    
    private void initComponents() {

        jFileChooser1 = new JFileChooser();
        nofilechoosen2label = new JLabel("No File Chosen");
        phraselabel2 = new JLabel("Phrase:");
        jPasswordField2 = new JTextField();
        keyfilechosen2label = new JLabel("No File Chosen");
        jScrollPane1 = new JScrollPane();
        jTextArea2 = new JTextArea(5,20);
        choosefile2button = new JButton("Choose File");
        choosekeyfile2button = new JButton("Choose Key File");
        read2button = new JButton("Read");
        exit2button = new JButton("Exit");
        f = new Font("Tahoma",0,18);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jTextArea2.setEditable(false);

        nofilechoosen2label.setFont(f);
        phraselabel2.setFont(f);
        keyfilechosen2label.setFont(f);
        jScrollPane1.setViewportView(jTextArea2);

        choosefile2button.setFont(f); 
        choosefile2button.addActionListener(this);

        choosekeyfile2button.setFont(f); 
        choosekeyfile2button.addActionListener(this);

        read2button.setFont(f);
        read2button.addActionListener(this);

        exit2button.setFont(f);
        exit2button.addActionListener(this);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(read2button, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit2button, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(choosefile2button, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(nofilechoosen2label, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(choosekeyfile2button)
                            .addGap(36, 36, 36)
                            .addComponent(keyfilechosen2label, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(phraselabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPasswordField2))))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nofilechoosen2label)
                    .addComponent(choosefile2button, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phraselabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(choosekeyfile2button, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(keyfilechosen2label))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exit2button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(read2button, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        pack();
    }

    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();

        if(src.equals(exit2button)){
            System.exit(0);
        }
        else if(src.equals(choosefile2button)){
            JFileChooser fileChooser = new JFileChooser("C:\\");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION){

                messageFile = fileChooser.getSelectedFile();
                nofilechoosen2label.setText(messageFile.getName());
            
            }
        }
        else if(src.equals(choosekeyfile2button)){
              JFileChooser fileChooser = new JFileChooser("C:\\");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION){

                keyFile = fileChooser.getSelectedFile();
                keyfilechosen2label.setText(keyFile.getName());
            
            }
        }
        else if(src.equals(read2button)){
            try{
                if(messageFile == null){
                    JOptionPane.showMessageDialog(null,"Message File Not Chosen","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(messageFile));
                FileContent fc = (FileContent)ois.readObject();
                ois.close();

                Cipher aes = Cipher.getInstance("AES");
                String phrase = jPasswordField2.getText();
                for(int i = phrase.length();i < 16;i++){
                    phrase = phrase.concat("a");
                }
                SecretKeySpec secretKey = new SecretKeySpec(phrase.getBytes(),"AES");
                aes.init(Cipher.DECRYPT_MODE,secretKey);
                String label = new String(aes.doFinal(fc.label));

                FileSystemView  fsv = FileSystemView.getFileSystemView();
                File[] f = File.listRoots();
                String thisLabel = fsv.getSystemDisplayName(f[0]);

                if(thisLabel.compareTo(label) != 0){
                    deleteFile();
                    System.exit(0);
                }

                BufferedReader br = new BufferedReader(new FileReader(keyFile));
                String str = br.readLine();
                Base64.Decoder decoder = Base64.getDecoder();

                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(decoder.decode(str));
                PrivateKey  privateKey = keyFactory.generatePrivate(privateKeySpec);

                Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                rsa.init(Cipher.DECRYPT_MODE,privateKey);
                jTextArea2.setText(new String(rsa.doFinal(fc.message)));
                deleteFile();

            }
            catch(StreamCorruptedException ex){
                JOptionPane.showMessageDialog(null,"Invalid Message File","Error",JOptionPane.WARNING_MESSAGE);   
            }
            catch(BadPaddingException ex){
                JOptionPane.showMessageDialog(null,"Invalid Key File or Phrase","Error",JOptionPane.WARNING_MESSAGE); 
            }
            catch(FileNotFoundException  ex){
                JOptionPane.showMessageDialog(null,"Message File Not Found","Error",JOptionPane.WARNING_MESSAGE);
            }
            catch(Exception ex){
                System.out.println(ex);
            }

        }
    }

    void deleteFile(){

        try{

            PrintWriter pr = new PrintWriter(new FileOutputStream(messageFile),true);
            pr.println("");
            pr.close();
            messageFile.delete();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        
    }


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Decrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Decrypt().setVisible(true);
            }
        });
    }

    private JButton choosefile2button;
    private JButton choosekeyfile2button;
    private JButton exit2button;
    private JFileChooser jFileChooser1;
    private JTextField jPasswordField2;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea2;
    private JLabel keyfilechosen2label;
    private JLabel nofilechoosen2label;
    private JLabel phraselabel2;
    private JButton read2button;
    private Font f;
    private File messageFile;
    private File keyFile;
}
