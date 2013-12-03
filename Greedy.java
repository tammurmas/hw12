/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author Urmas
 */
public class Greedy {
    
    private static final String fileName = "task_001.txt";
    private ArrayList<TreeSet> sets;
    private TreeSet universe;
    private TreeSet cover;
    
    public Greedy() throws IOException
    {
        this.sets = new ArrayList<TreeSet>();
        this.cover = new TreeSet();
        
        BufferedReader inputStream = null;

        String line;
        
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            
            while( (line = inputStream.readLine()) != null)
            {
                String[] lineParts = line.split("\\s+");//any number of whitespace characters as delimiter
                
                if("SET_COVER".equals(lineParts[0]))
                {
                    String[] limits = lineParts[1].split("\\..");
                    
                    int start = Integer.parseInt(limits[0]);
                    int end   = Integer.parseInt(limits[1]);
                    
                    this.universe = new TreeSet();
                    
                    for(int i=start; i<=end; i++)
                    {
                        this.universe.add(i);
                    }
                    
                }
                else
                {
                    TreeSet values = new TreeSet();
                    for(int i=2; i<lineParts.length; i++)
                    {
                        values.add(Integer.parseInt(lineParts[i]));
                    }
                    this.sets.add(values);
                }
            }
        }
        catch (IOException e){}
        finally{
             if (inputStream != null) {
                inputStream.close();
            }
        }
    }
    
    public static void main(String[] args) throws IOException
    {
        
        Greedy greedy = new Greedy();
        
        //greedy.printUniverse();
        
        greedy.universe.remove(1);
        
        greedy.printUniverse();
    }
    
    public void printSets()
    {
        for(int i=0; i<this.sets.size(); i++)
        {
            TreeSet set = this.sets.get(i);
            System.out.print((i+1)+": ");
            while(set.size() > 0)
            {
                System.out.print(set.pollFirst()+" ");
            }
            System.out.println();
        }
    }
    
    public void printUniverse()
    {
        while(this.universe.size() > 0)
        {
            System.out.print(this.universe.pollFirst()+" ");
        }
    }
}
