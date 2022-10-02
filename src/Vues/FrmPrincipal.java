package Vues;

import Entities.Tache;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class FrmPrincipal extends JFrame {
    private JList lstTheme;
    private JList lstProjet;
    private JTree trResultat;
    private JTextField txtTache;
    private JComboBox cboChoix;
    private JButton btnValider;
    private JPanel pnlRoot;
    private JLabel lblTitle;


    private HashMap<String, HashMap<String, ArrayList<Tache>>> monPlanning;
    private HashMap<String,ArrayList<Tache>> mesProjets;
    private ArrayList<Tache> taches;
    DefaultMutableTreeNode root;
    DefaultTreeModel model;
    public FrmPrincipal() {
        this.setTitle("Projet");
        this.setContentPane(pnlRoot);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                monPlanning = new HashMap<String, HashMap<String, ArrayList<Tache>>>() ;

                root = new DefaultMutableTreeNode("Les tâches à faire");
                model = new DefaultTreeModel(root);
                trResultat.setModel(model);
            }
        });

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(txtTache.getText().compareTo("")==0)
                {
                    JOptionPane.showMessageDialog(null,"Veuillez entrer le nom d'une tâche à effectuer","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
                }
                else if(lstTheme.getSelectedValue() == null)
                {
                    JOptionPane.showMessageDialog(null,"Veuillez sélectionner un thème","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
                }
                else if(lstProjet.getSelectedValue() == null)
                {
                    JOptionPane.showMessageDialog(null,"Veuillez sélectionner un Projet","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    Tache maTache = new Tache(txtTache.getText(), cboChoix.getSelectedItem().toString(), false);
//                    if (monPlanning.containsKey(lstTheme.getSelectedValue().toString())) {
//                        if(monPlanning.get(lstTheme.getSelectedValue().toString()).containsKey(lstProjet.getSelectedValue().toString())) {
//                            monPlanning.get(lstTheme.getSelectedValue().toString()).get(lstProjet.getSelectedValue().toString()).add(maTache);
//                        }
//                        else {
////                            ArrayList<Tache> lesTaches = new ArrayList<>();
////                            lesTaches.add(maTache);
//                            maTache = new ArrayList<>();
//                            maTache.add(maTache);
//                            monPlanning.get(lstTheme.getSelectedValue().toString()).put(lstProjet.getSelectedValue().toString(), lesTaches);
//                        }
//                    }
                    if (!monPlanning.containsKey(lstTheme.getSelectedValue())) { // S'il n'existe pas
                        mesProjets = new HashMap<>(); // on reset le hash map
                        taches = new ArrayList<>(); // on reset le hash map
                        taches.add(maTache); // on ajoute l'objet au tableau
                        mesProjets.put(lstProjet.getSelectedValue().toString(), taches); // on ajoute le tableau avec la clé qu'on get la valeur en la transformant en string dans mesProjets
                        monPlanning.put(lstTheme.getSelectedValue().toString(), mesProjets); // on ajoute le HashMap avec la clé qu'on get la valeur en la transformant en string dans monPlanning
                    } else { // S'il existe dans la clé alors
                        mesProjets = new HashMap<>();
                        if (monPlanning.get(lstTheme.getSelectedValue().toString()).containsKey(lstProjet.getSelectedValue().toString())){
                            monPlanning.get(lstTheme.getSelectedValue().toString()).get(lstProjet.getSelectedValue().toString()).add(maTache);
                        }else {
                            taches = new ArrayList<>(); // on reset le hash map
                            taches.add(maTache); // on ajoute l'objet au tableau
                            monPlanning.get(lstTheme.getSelectedValue().toString()).put(lstProjet.getSelectedValue().toString(), taches);
                        }
                    }
                        DefaultMutableTreeNode noeudTheme = null;
                        DefaultMutableTreeNode noeudProjet = null;
                        DefaultMutableTreeNode noeudTache = null;

                        root.removeAllChildren();

                        for (String theme : monPlanning.keySet())
                        {
                            noeudTheme = new DefaultMutableTreeNode(theme);

                            for (String projet : monPlanning.get(theme).keySet())
                            {
                                noeudProjet = new DefaultMutableTreeNode(projet);

                                for (Tache tache : monPlanning.get(theme).get(projet)) {
                                    noeudTache = new DefaultMutableTreeNode(tache.getNomTache());
                                    noeudProjet.add(noeudTache);
                                    noeudTache = new DefaultMutableTreeNode(tache.getNomPersonne());
                                    noeudProjet.add(noeudTache);
                                    noeudTache = new DefaultMutableTreeNode(tache.isTermine());
                                    noeudProjet.add(noeudTache);
                                }
                                noeudTheme.add(noeudProjet);
                            }
                            root.add(noeudTheme);
                        }
                        trResultat.setModel(new DefaultTreeModel(root));
                    }
                }
        });
    }
}
