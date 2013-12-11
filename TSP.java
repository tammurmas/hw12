/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tamm.aa.hw12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * TSP using the nearest neighbour approach
 * 
 * @author Urmas T.
 */
public class TSP {
    private static final String fileName = "TSP_10.txt";

    public static void main(String[] args) throws IOException {
        
        BufferedReader inputStream = null;

        String line;
        
        try {
            inputStream = new BufferedReader(new FileReader(fileName));
            
            int i = 0;
            
            while( (line = inputStream.readLine()) != null)
            {
                //skip first line
                if(i > 0)
                {
                    String[] lineParts = line.split("\\s+");//any number of whitespace characters as delimiter
                
                    int x = Integer.parseInt(lineParts[0]);
                    int y = Integer.parseInt(lineParts[1]);
                    
                    City city = new City(x,y, i);

                    TourManager.addCity(city);
                            
                }
                i++;
            }
        }
        catch (IOException e){}
        finally{
             if (inputStream != null) {
                inputStream.close();
            }
        }
        
       nearestNeighbor();
    }
    
    /**
     * Calculating the path using the nearest neighbor algorithm
     */
    public static void nearestNeighbor()
    {
        Tour tour = new Tour();
        ArrayList<Integer> visited = new ArrayList<Integer>();
        
        City current = TourManager.getCity(0);//add the first city into our tour
        tour.setCity(0, current);
        
        visited.add(current.getI());
        
        double minDistance = Double.MAX_VALUE;
        int nextId = current.getI();
        
        int n = 1;
        
        while (visited.size() < TourManager.numberOfCities())
        {
            
            for (int i=1; i<TourManager.numberOfCities(); i++)
            {
                City city = TourManager.getCity(i);
                
                //put aside cities we've already been to
                if(visited.contains(city.getI()) == false)
                {
                    double distance = current.distanceTo(city);
                    
                    if(distance < minDistance)
                    {
                        minDistance = distance;
                        nextId = city.getI();
                    }
                }
                    
            }
            
            current = TourManager.getCity(nextId-1);//set next city as current
            visited.add(nextId);
            
            tour.setCity(n, current);//add to tour
            n++;
            
            minDistance = Double.MAX_VALUE;
            
        }
        
        System.out.println(tour);
        System.out.println(tour.getDistance());
        
    }
}
