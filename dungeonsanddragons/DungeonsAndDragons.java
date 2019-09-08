/*
 * Name    : Jagannathan Harshavardhan
 * User ID : hxj4805
 * Lab #   : Final exam
 */
package dungeonsanddragons;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class DungeonsAndDragons 
{
    public static void main(String[] args) throws FileNotFoundException 
    {
        Scanner scan = new Scanner(System.in);
        Scanner junior = new Scanner(new File("instructions2.txt"));
        
        while(junior.hasNextLine())
        {
            System.out.println(junior.nextLine());
        }
        
        System.out.print("          Do you want to play single player or multiplayer? Press 1 for single, 2 for multi :: ");
        int ans = scan.nextInt();
        
        MazeGui jeroo = new MazeGui(ans);
    }
}