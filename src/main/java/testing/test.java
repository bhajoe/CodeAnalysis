/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

/**
 *
 * @author bayup
 */
public class test {
    public static void main()
    {
        int a = 0;
        int b = 0;
        int c = 0;
        int avg;
        avg = (a + b + c) / 3;
        System.out.printf("avg = %d",avg);
        while (true)
        {
            
        }
    }
    
    public int hitung (int p, int l)
    {
        int hasil;
        hasil = p * l;
        if ((hasil > 20) && (hasil < 40) && (hasil > 15))
        {
            System.out.println("A");
        } else if (hasil == 10)
        {
            System.out.println("B");
        } else if (hasil == 1)
        {
            
        }
        while ((hasil > 10) && (hasil <=20))
        {
            hasil++;
        }
        
        for (int i = 1; i <= 5; ++i) {

         System.out.println("Outer loop iteration " + i);

         for (int j = 1; j <=2; ++j) {
            System.out.println("i = " + i + "; j = " + j);
         }
        
        }
        return hasil;
    }

    public float average()
    {
        int[] value = null;
        int i, minimum = 0, maximum = 0;
        i = 1;
        int total_input = 0;
        int total_valid = 0;
        int sum = 0;
        
        while ((value[i] != -999) && (total_input <  100))
        {
            total_input++;
            if ((value[i] >= minimum) && (value[i] <= maximum))
            {
                total_valid++;
                sum += value[i];
            } 
            i++;
        }
        
        float hasil;
        if (total_valid>0)
        {
            hasil = (sum/total_valid);
            return hasil;
        } else
        {
            return -999;
        }
        
    }
    
}
