package gui;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import back.RDFManager;
import back.TwitterWrapper;
import org.apache.jena.atlas.logging.LogCtl;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import twitter4j.Status;
import twitter4j.TwitterException;

public class App extends javax.swing.JFrame {
    
    public App() {
        LogCtl.setCmdLogging();
        initComponents();
        this.setTitle("Creador de ficheros RDF");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoFormato = new javax.swing.ButtonGroup();
        tweetsNumberLabel = new javax.swing.JLabel();
        patternField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        rdfXmlRadio = new javax.swing.JRadioButton();
        turtleRadio = new javax.swing.JRadioButton();
        outputFormatLabel = new javax.swing.JLabel();
        tweetsNumberSpinner = new javax.swing.JSpinner();
        patternLabel = new javax.swing.JLabel();
        themeLabel = new javax.swing.JLabel();
        themeField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tweetsNumberLabel.setText("Número de tweets");

        patternField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patternFieldActionPerformed(evt);
            }
        });

        searchButton.setText("Crear");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        grupoFormato.add(rdfXmlRadio);
        rdfXmlRadio.setText("RDF/XML ");

        grupoFormato.add(turtleRadio);
        turtleRadio.setSelected(true);
        turtleRadio.setText("Turtle");

        outputFormatLabel.setText("Formato de salida");

        tweetsNumberSpinner.setModel(new javax.swing.SpinnerNumberModel(10, 1, 200, 1));
        tweetsNumberSpinner.setToolTipText("");
        tweetsNumberSpinner.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tweetsNumberSpinner.setName("tweetsNumberSpinner"); // NOI18N

        patternLabel.setText("Patrón de búsqueda");

        themeLabel.setText("Tema");

        themeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                themeFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(outputFormatLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(turtleRadio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdfXmlRadio))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(themeLabel)
                        .addComponent(patternLabel)
                        .addComponent(patternField)
                        .addComponent(themeField)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(searchButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                            .addComponent(tweetsNumberLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tweetsNumberSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(patternLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patternField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(themeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(themeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tweetsNumberLabel)
                    .addComponent(tweetsNumberSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(outputFormatLabel)
                    .addComponent(turtleRadio)
                    .addComponent(rdfXmlRadio))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        search();        
    }//GEN-LAST:event_searchButtonActionPerformed
    
    private void search(){
        if (patternField.getText().trim().isEmpty() || 
               themeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Campos incorrectos", "Error", JOptionPane.WARNING_MESSAGE);
        } else {        
            try {
                List<Status> tweets = TwitterWrapper.searchTweets(patternField.getText(),(Integer)tweetsNumberSpinner.getValue());
                RDFManager manager = new RDFManager(patternField.getText(), themeField.getText());
                tweets.forEach((tweet) -> {
                    manager.createGraph(tweet);
                });
                save(manager.getModel());
            } catch(TwitterException e) {
                JOptionPane.showMessageDialog(this, "Hubo un error al realizar la busqueda", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
    
    private void save(Model model){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if(result == JFileChooser.CANCEL_OPTION)return;
        if(result == JFileChooser.APPROVE_OPTION);
        String fileName = fileChooser.getSelectedFile().getAbsolutePath();
        
        try {
            FileWriter writer;
            if (turtleRadio.isSelected()){
                writer = new FileWriter(fileName + ".ttl");
                RDFDataMgr.write(writer, model, RDFFormat.TURTLE);
                writer.close();
            }else if (rdfXmlRadio.isSelected()){
                writer = new FileWriter(fileName + ".rdf");
                RDFDataMgr.write(writer, model, RDFFormat.RDFXML);
                writer.close();
            }
            JOptionPane.showMessageDialog(this, "Archivo generado con éxito");
        } catch(IOException e){
            JOptionPane.showMessageDialog(this, "Hubo un error al crear el archivo");
        }
    }
    
    private void patternFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patternFieldActionPerformed
        search();
    }//GEN-LAST:event_patternFieldActionPerformed

    private void themeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_themeFieldActionPerformed
        search();
    }//GEN-LAST:event_themeFieldActionPerformed

    public static void main(String args[]) {
        App frame = new App();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup grupoFormato;
    private javax.swing.JLabel outputFormatLabel;
    private javax.swing.JTextField patternField;
    private javax.swing.JLabel patternLabel;
    private javax.swing.JRadioButton rdfXmlRadio;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField themeField;
    private javax.swing.JLabel themeLabel;
    private javax.swing.JRadioButton turtleRadio;
    private javax.swing.JLabel tweetsNumberLabel;
    private javax.swing.JSpinner tweetsNumberSpinner;
    // End of variables declaration//GEN-END:variables
}