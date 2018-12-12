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
    }
    
    public int hitung (int p, int l)
    {
        int hasil;
        hasil = p * l;
        if ((hasil > 20) && (hasil < 40))
        {
            System.out.println("A");
        } else
        {
            System.out.println("B");
        }
        return hasil;
    }

    
}
