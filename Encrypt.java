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

public class Encrypt extends JFrame implements ActionListener{

    public Encrypt() {
        super("No Whisper");
        initComponents();
    }

    private void initComponents() {

        jFileChooser1 = new JFileChooser();
        jOptionPane1 = new JOptionPane();
        phraselabel = new JLabel("Phrase:");
        jScrollPane1 = new JScrollPane();
        jTextArea1 = new JTextArea(5,20);
        jPasswordField1 = new JTextField();
        jLabel2 = new JLabel();
        nofilechooselabel = new JLabel("No File Chosen");
        enteryourmessagelabel = new JLabel("Message");
        generatekeybutton = new JButton("Generate Key");
        choosekeybutton = new JButton("Choose Key File");
        generatebutton = new JButton("Generate File");
        exitbutton = new JButton("Exit");
        f = new Font("Tahoma", 0, 18);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        phraselabel.setFont(f);

        jScrollPane1.setViewportView(jTextArea1);

        jPasswordField1.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e){
                if(jPasswordField1.getText().length() >= 16){
                    e.consume();
                }
            }
        });

        jTextArea1.addKeyListener(new KeyAdapter(){
             public void keyTyped(KeyEvent e){
                if(jTextArea1.getText().length() >= 500){
                    e.consume();
                }
            }
        });


        nofilechooselabel.setFont(f); 
        enteryourmessagelabel.setFont(f);

        generatekeybutton.setFont(f);
        generatekeybutton.addActionListener(this);

        choosekeybutton.setFont(f);
        choosekeybutton.addActionListener(this);

        generatebutton.setFont(f);
        generatebutton.addActionListener(this);

        exitbutton.setFont(f);
        exitbutton.addActionListener(this);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel2)
                .addContainerGap(285, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(enteryourmessagelabel,GroupLayout.PREFERRED_SIZE, 182,GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(generatebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(exitbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(generatekeybutton)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(phraselabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPasswordField1))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(choosekeybutton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(nofilechooselabel))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(generatekeybutton)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phraselabel)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(choosekeybutton)
                    .addComponent(nofilechooselabel))
                .addGap(32, 32, 32)
                .addComponent(enteryourmessagelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generatebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exitbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37))
        );

        pack();
    }

    public void actionPerformed(ActionEvent e){
        Object src = e.getSource();

        if(src.equals(exitbutton)){
            System.exit(0);
        }
        else if(src.equals(generatekeybutton)){
            try{
                    KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
                    kpg.initialize(1024);
                    KeyPair kp = kpg.generateKeyPair();
                    Key publicKey = kp.getPublic();
                    Key privateKey = kp.getPrivate();
                    Base64.Encoder encoder = Base64.getEncoder();
                    PrintWriter pr = new PrintWriter(new FileOutputStream("public.txt"),true);
                    pr.println(encoder.encodeToString(publicKey.getEncoded()));

                    pr = new PrintWriter(new FileOutputStream("private.txt"),true);
                    pr.println(encoder.encodeToString(privateKey.getEncoded()));
                }catch(Exception ex){}
        }
        else if(src.equals(choosekeybutton)){
            JFileChooser fileChooser = new JFileChooser("C:\\");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            
            if (result == JFileChooser.APPROVE_OPTION){

                keyFile = fileChooser.getSelectedFile();
                nofilechooselabel.setText(keyFile.getName());
            
            }
        }
        else if(src.equals(generatebutton)){
            try{
                if(keyFile == null){
                    JOptionPane.showMessageDialog(null,"Key File Not Selected","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(jTextArea1.getText().length() == 0){
                    JOptionPane.showMessageDialog(null,"Message is Empty","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Cipher aes;
                String phrase = jPasswordField1.getText();
                for(int i = phrase.length();i < 16;i++){
                    phrase = phrase.concat("a");
                }
                SecretKeySpec secretKey = new SecretKeySpec(phrase.getBytes(),"AES");
                aes = Cipher.getInstance("AES");
                aes.init(Cipher.ENCRYPT_MODE,secretKey);

                FileContent fc = new FileContent();

                FileSystemView  fsv = FileSystemView.getFileSystemView();
                File[] f = File.listRoots();
                fc.label = aes.doFinal(fsv.getSystemDisplayName(f[0]).getBytes());
                
                Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");

                BufferedReader br = new BufferedReader(new FileReader(keyFile));
                String str = br.readLine();

                Base64.Decoder decoder = Base64.getDecoder();
                byte[] keyBytes = decoder.decode(str);
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(keyBytes);


                KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                rsa.init(Cipher.ENCRYPT_MODE,publicKey);

                String message = jTextArea1.getText();
                fc.message = rsa.doFinal(message.getBytes());

                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("message.nw"));

                oos.writeObject(fc);
                oos.close();

                JOptionPane.showMessageDialog(null,"message.nw Generated","Generate",JOptionPane.INFORMATION_MESSAGE);

            }
            catch(Exception ex){
                System.out.println(ex);
            }
            
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
            java.util.logging.Logger.getLogger(Encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Encrypt.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Encrypt().setVisible(true);
            }
        });
    }

    private JButton choosekeybutton;
    private JLabel enteryourmessagelabel;
    private JButton exitbutton;
    private JButton generatebutton;
    private JButton generatekeybutton;
    private JFileChooser jFileChooser1;
    private JLabel jLabel2;
    private JOptionPane jOptionPane1;
    private JTextField jPasswordField1;
    private JScrollPane jScrollPane1;
    private JTextArea jTextArea1;
    private JLabel nofilechooselabel;
    private JLabel phraselabel;
    private Font f;
    private File keyFile;
}
