import java.util.Random;

public class nQueens {
    //create list of chromosomes and counter for the number of conflicts in a given generation
    public Chromosome[] chromosomes = new Chromosome[8];
    public int[] conflicts = {0,0,0,0,0,0,0,0};
    public int[] qPosition = new int[8];

    nQueens(){
        
        //initialize the chromosomes and print them
        for(int i = 0; i<8; i++)
        {
            this.chromosomes[i] = new Chromosome();
        }
    
    }

    public void generation()
    {
    
        Random r = new Random();
        int count = r.nextInt(4);
        for(int i = 0; i<count;i++)
        {
            int test = r.nextInt(8);
            this.chromosomes[test].mutate();
        }
        

    }
    public void fitness()
    {
        for(int x = 0; x<8; x++)
        {
            //reset the conflicts array counter
            this.conflicts[x] = 0;

            //determine the logical position of the queen
            int position = 0;
            if(this.chromosomes[x].a)
            {
                position+=4;
            }
            if(this.chromosomes[x].b)
            {
                position+=2;
            }
            if(this.chromosomes[x].c)
            {
                position+=1;
            }
            this.qPosition[x] = position;
        }
        for(int i = 0; i<7; i++)
        {
            for(int j = i+1; j<8; j++)
            {
                if(this.qPosition[i] == this.qPosition[j])
                {
                    //Check if the two queens are in the same column
                    this.conflicts[i]++;
                    this.conflicts[j]++;
                }

                if(Math.abs(this.qPosition[j]-this.qPosition[i]) == j-i)
                {
                    //Check if the two queens are in the same diagonal
                    this.conflicts[i]++;
                    this.conflicts[j]++;
                }
            }
        }
    }

    public void printBoard()
    {
        for(int x = 0; x<8; x++)
        {
            int position = 0;
            String row = "";
            if(this.chromosomes[x].a)
            {
                position+=4;
            }
            if(this.chromosomes[x].b)
            {
                position+=2;
            }
            if(this.chromosomes[x].c)
            {
                position+=1;
            }
            for(int y = 0; y<position; y++)
            {
                row +="-";
            }
            row +="Q";
            for(int y = position+1; y<8; y++)
            {
                row +="-";
            }
            System.out.println(row);
        }
    }
}

class Chromosome
{
    boolean a;
    boolean b;
    boolean c;

    Chromosome()
    {
        Random r = new Random();
        int test = r.nextInt(2);
        if(test%2 == 0)
        {
            a = false;
        }
        else
        {
            a = true;
        }
        test = r.nextInt(2);
        if(test%2 == 0)
        {
            b = false;
        }
        else
        {
            b = true;
        }
        test = r.nextInt(2);
        if(test%2 == 0)
        {
            c = false;
        }
        else
        {
            c = true;
        }
    }

    public void print(){
        String value = "";
        if(a)
        {
            value+="1";
        }
        else
        {
            value+="0";
        }
        if(b)
        {
            value+="1";
        }
        else
        {
            value+="0";
        }
        if(c)
        {
            value+="1";
        }
        else
        {
            value+="0";
        }

        System.out.println(value);
    }

    public void mutate()
    {
        Random r = new Random();
        int test = r.nextInt(3);
        if(test == 0)
        {
            if(a){ a=false; }
            else { a=true; }
        }
        else if(test == 1)
        {
            if(b){ b=false; }
            else { b=true; }
        }
        else
        {
            if(c){ c=false; }
            else { c=true; }
        }
    }
}