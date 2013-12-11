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
    
    private static String fileName = "task_001.txt";
    private ArrayList<TreeSet> sets;
    private ArrayList<Double> weights;
    private TreeSet universe;
    
    public Greedy() throws IOException
    {
        this.sets = new ArrayList<TreeSet>();
        this.weights = new ArrayList<Double>();
        
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
                    String[] weightParts = lineParts[1].split("\\=");
                    this.weights.add(Double.parseDouble(weightParts[1]));
                    
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
        
        for(int i=1; i<=30; i++)
        {
            if(i < 10)
                fileName = "task_00"+i+".txt";
            else
                fileName = "task_0"+i+".txt";
            
            Greedy greedy = new Greedy();
            
            System.out.print("task_"+i+": nonw_greedy:");
            greedy.cover();
            System.out.println();
            
            System.out.print("task_"+i+": weig_greedy:");
            
            Greedy weighGreedy = new Greedy();
            weighGreedy.weightedCover();
            System.out.println();
        }
        
        
    }
    
    /**
     * HINT: http://www.cs.uiuc.edu/class/sp08/cs473/Lectures/lec20.pdf
     * Calculates the weighted cover for the given object
     */
    public void weightedCover()
    {
        double total_weight = 0;
        
        while(this.universe.size() > 0)
        {
            double min_weight = Double.MAX_VALUE;
            int max_index     = 0;
            
            for(int i=0; i<this.sets.size(); i++)
            {
                
                TreeSet set = (TreeSet)this.sets.get(i).clone();

                int elems = 0;//number of intercepting elements in the current set
                double weight = this.weights.get(i);//set's weight
                
                while(set.size() > 0)
                {
                    if(this.universe.contains(set.pollFirst()))
                    {
                        elems++;
                    }
                }

                //minimize the cover
                if(weight / elems < min_weight)
                {
                    max_index = i;
                    min_weight = weight / elems;
                }
                    
            }
            
            System.out.print((max_index+1)+" ");
            
            total_weight += min_weight;
            
            this.remElements(max_index);
        }
        
        System.out.println(Math.ceil(total_weight*100)/100);
    }
    
    /**
     * Calculates the regular cover for the given object
     */
    public void cover()
    {
        int max_size = 0;
        int max_index = 0;
        
        //pick the largest set to maximize the initial cover
        for(int i=0; i<this.sets.size(); i++)
        {
            TreeSet set = this.sets.get(i);
            
            if(set.size() > max_size)
            {
                max_size = set.size();
                max_index = i;
            }
        }
        
        System.out.print((max_index+1)+" ");
        
        this.remElements(max_index);
        
        while(this.universe.size() > 0)
        {
            max_index = 0;
            max_size  = 0;
            
            for(int i=0; i<this.sets.size(); i++)
            {
                
                TreeSet set = (TreeSet)this.sets.get(i).clone();

                int elems = 0;
                
                while(set.size() > 0)
                {
                    if(this.universe.contains(set.pollFirst()))
                    {
                        elems++;
                    }
                }

                //minimize the size of the cover, so take set with less in size
                if(elems > max_size)
                {
                    max_index = i;
                    max_size = elems;
                }
                    
            }

            System.out.print((max_index+1)+" ");

            this.remElements(max_index);
        }
    }
    
    /**
     * Clear the elements of the set with the given id
     * @param max_index 
     */
    public void remElements(int max_index)
    {
        TreeSet maxSet = this.sets.get(max_index);
        this.universe.removeAll(maxSet);//remove values in the set we found from universe
        maxSet.clear();//clear the set
        //printSet(maxSet);
    }
    
    /**
     * Helper to print all sets of the given object
     */
    public void printSets()
    {
        for(int i=0; i<this.sets.size(); i++)
        {
            TreeSet set = (TreeSet)this.sets.get(i).clone();
            System.out.print((i+1)+": ");
            while(set.size() > 0)
            {
                System.out.print(set.pollFirst()+" ");
            }
            System.out.println();
        }
    }
    
    /**
     * Helper to print the universe of the given object
     */
    public void printUniverse()
    {
        TreeSet uni = (TreeSet)this.universe.clone();
        
        while(uni.size() > 0)
        {
            System.out.print(uni.pollFirst()+" ");
        }
    }
    
    /**
     * Helper to print the given set
     * @param set 
     */
    public static void printSet(TreeSet set)
    {
        while(set.size() > 0)
        {
            System.out.print(set.pollFirst()+" ");
        }    
        System.out.println();
    }
}