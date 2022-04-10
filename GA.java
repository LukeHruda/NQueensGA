import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GA {
    //create list of chromosomes and counter for the number of conflicts in a given generation
    public static nQueens[] boards = new nQueens[500];
    public static ArrayList<String> solutions = new ArrayList<>();
    public static int[] fitnessList = new int[500];
    public static void main(String[] args) {

        //initialize the chess boards and check their fitness
        for(int i = 0; i<boards.length; i++)
        {   
            boards[i] = new nQueens();
            boards[i].fitness();
        }
        //values for checking a generation
        int sum = 0;
        int value;
        int max = 0;
        int min = Integer.MAX_VALUE;
        double average;
        //go through all the boards 
        for(int i = 0; i<boards.length; i++)
        { 
            //get the sum of their conflicts
            value = Arrays.stream(boards[i].conflicts).sum();
            //add value to array for creating next generation
            fitnessList[i] = value;
            //if the value is 0 then the system has found a solution
            if(value == 0)
            {
                //Generate unique solution key
                String qPosList = "";
                for(int x = 0; x<8; x++)
                {
                    qPosList += Integer.toString(boards[i].qPosition[x]);
                }
                //If the solution key is already in the list of solutions dont add it
                if(!solutions.contains(qPosList))
                {
                    solutions.add(qPosList);
                    System.out.println("New Solution found!");
                }
            }

            //increase the sum, check if this is the most or least conflicts for this generation
            sum+=value;
            if(value > max)
            {
                max = value;
            }
            if(value < min)
            {
                min = value;
            }
        }

        //calc average
        average = sum / boards.length;

        //print generation stats
        System.out.println("Inital Generation: Average Fitness: "+average+" Best Fit: "+min+" Worst Fit: "+max +" Solutions found: "+solutions.size());

        //stats required for finding best fit solutions to cossover
        int best = Integer.MAX_VALUE;
        int secBest = Integer.MAX_VALUE;
        int worst = 0;
        int secWorst = 0;
        int bestPos = 0;
        int secBestPos = 0;
        int worstPos = 0;
        int secWorstPos = 0;
        //since we crossover the best 2 overwriting the worst 2 and so on, we only need to loop 1/4 of the list of boards
        for(int i = 0; i<boards.length/4; i++)
        {  
            for(int j = 0;j<boards.length;j++)
            {
                //dont check items that have already been checked
                if(fitnessList[j] > -1)
                {
                    //if its the best fit get its position
                    if(fitnessList[j]<best)
                    {
                        secBest = best;
                        secBestPos = bestPos;
                        best = fitnessList[j];
                        bestPos = j;
                        fitnessList[j] = -1;
                    }
                    //if its the second best fit get its position
                    else if((best<fitnessList[j])&&(fitnessList[j]<secBest))
                    {
                        secBest = fitnessList[j];
                        secBestPos = j;
                        fitnessList[j] = -1;
                    }
                    //if its the worst fit get its position
                    else if(fitnessList[j]>worst)
                    {
                        secWorst = worst;
                        secWorstPos = worstPos;
                        worst = fitnessList[j];
                        worstPos = j;
                        fitnessList[j] = -2;
                    }
                    //if its the second worst fit get its position
                    else if((worst>fitnessList[j])&&(fitnessList[j]>secWorst))
                    {
                        secWorst = fitnessList[j];
                        secWorstPos = j;
                        fitnessList[j] = -2;
                    }
                }
            }
            //crossover the best and second best over the worst and second worst
            for(int k = 0; k<8;k++)
            {
                Random r = new Random();
                int test = r.nextInt(2);
                if(test%2 == 0)
                {
                    boards[worstPos].chromosomes[k] = boards[bestPos].chromosomes[k];
                    boards[secWorstPos].chromosomes[k] = boards[secBestPos].chromosomes[k];
                }
                else
                {
                    boards[secWorstPos].chromosomes[k] = boards[bestPos].chromosomes[k];
                    boards[worstPos].chromosomes[k] = boards[secBestPos].chromosomes[k];
                }
            }

        }
        //repeat the above until all 92 solutions have been found
        int generation = 2;
        while(solutions.size()<92)
        {
            max = 0;
            min = Integer.MAX_VALUE;
            sum = 0;
            for(int i = 0; i<boards.length; i++)
            { 
                boards[i].generation();
                boards[i].fitness();
                value = Arrays.stream(boards[i].conflicts).sum();
                if(value == 0)
                {
                    String qPosList = "";
                    for(int x = 0; x<8; x++)
                    {
                        qPosList += Integer.toString(boards[i].qPosition[x]);
                    }
                    //System.out.println(qPosList);
                    if(!solutions.contains(qPosList))
                    {
                        solutions.add(qPosList);
                        System.out.println("New Solution found!");
                    }
                }
                sum+=value;
                if(value > max)
                {
                    max = value;
                }
                if(value < min)
                {
                    min = value;
                }
            }
            average = sum / boards.length;

            System.out.println("Generation "+generation+": Average Fitness: "+average+" Best Fit: "+min+" Worst Fit: "+max+" Solutions found: "+solutions.size());
            generation++;
            best = Integer.MAX_VALUE;
            secBest = Integer.MAX_VALUE;
            worst = 0;
            secWorst = 0;
            bestPos = 0;
            secBestPos = 0;
            worstPos = 0;
            secWorstPos = 0;
            for(int i = 0; i<boards.length/4; i++)
            {  
                for(int j = 0;j<boards.length;j++)
                {
                    if(fitnessList[j] > -1)
                    {
                        if(fitnessList[j]<best)
                        {
                            secBest = best;
                            secBestPos = bestPos;
                            best = fitnessList[j];
                            bestPos = j;
                            fitnessList[j] = -1;
                        }
                        else if((best<fitnessList[j])&&(fitnessList[j]<secBest))
                        {
                            secBest = fitnessList[j];
                            secBestPos = j;
                            fitnessList[j] = -1;
                        }
                        else if(fitnessList[j]>worst)
                        {
                            secWorst = worst;
                            secWorstPos = worstPos;
                            worst = fitnessList[j];
                            worstPos = j;
                            fitnessList[j] = -1;
                        }
                        else if((worst>fitnessList[j])&&(fitnessList[j]>secWorst))
                        {
                            secWorst = fitnessList[j];
                            secWorstPos = j;
                            fitnessList[j] = -1;
                        }
                    }
                }

                for(int k = 0; k<8;k++)
                {
                    Random r = new Random();
                    int test = r.nextInt(2);
                    if(test%2 == 0)
                    {
                        boards[worstPos].chromosomes[k] = boards[bestPos].chromosomes[k];
                        boards[secWorstPos].chromosomes[k] = boards[secBestPos].chromosomes[k];
                    }
                    else
                    {
                        boards[secWorstPos].chromosomes[k] = boards[bestPos].chromosomes[k];
                        boards[worstPos].chromosomes[k] = boards[secBestPos].chromosomes[k];
                    }
                }
            }
        }
        //write all the solutions to the solutions.txt file
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("Solutions.txt"));
            
            for(int i = 0; i <92;i++)
            {
                out.write("Solution: "+(i+1)+" "+solutions.get(i)+"\n");
                String row = "";
                for(int x = 0; x<8; x++)
                {
                    int position = solutions.get(i).charAt(x) - '0';
                    row = "";
                    for(int y = 0; y<position; y++)
                    {
                        row +="-";
                    }
                    row +="Q";
                    for(int y = position+1; y<8; y++)
                    {
                        row +="-";
                    }
                    out.write(row+"\n");
                }
            }
            out.close();
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
}