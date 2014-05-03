package PonySolution.ui;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author Apostolidis
 */
public class ListManager {
    
    public static void AbstractToDefaultListModelConverter(javax.swing.JList list){
        DefaultListModel defaultListModel = new DefaultListModel();
        ListModel abstractListModel = list.getModel();
        for(int i=0; i<abstractListModel.getSize(); ++i){
            defaultListModel.addElement(abstractListModel.getElementAt(i));
        }
        list.setModel(defaultListModel);    
    }
    
    public static boolean removeSelectedFromList(javax.swing.JList list){
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        int selectedIndex = list.getSelectedIndex();
        if(selectedIndex!=-1){
            listModel.remove(selectedIndex);
            return true;
        }else
            return false;
    }
    
    public static void removeAllFromList(javax.swing.JList list){
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        listModel.clear();
    }
    
    
    public static void addStringToList(String str, javax.swing.JList list){
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        if(listModel==null){
            listModel = new DefaultListModel(); 
            list.setModel(listModel);
        }
        listModel.addElement(str);
    }
    
    public static  Collection<? extends String> getValuesFromList(javax.swing.JList list){
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        ArrayList<String> values = new ArrayList(listModel.getSize());
        for(int i=0; i<listModel.getSize(); ++i){
            values.add((String) listModel.getElementAt(i));
        }
        return values;
    }
    
    public static String getValueFromList(int index, javax.swing.JList list){
        DefaultListModel listModel = (DefaultListModel) list.getModel();
        return (String) listModel.get(index);
    }
}
