import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.*;

public class MovieCollection
{
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName)
    {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies()
    {
        return movies;
    }

    public void menu()
    {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q"))
        {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q"))
            {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option)
    {
        if (option.equals("t"))
        {
            searchTitles();
        }
        else if (option.equals("c"))
        {
            searchCast();
        }
        else if (option.equals("k"))
        {
            searchKeywords();
        }
        else if (option.equals("g"))
        {
            listGenres();
        }
        else if (option.equals("r"))
        {
            listHighestRated();
        }
        else if (option.equals("h"))
        {
            listHighestRevenue();
        }
        else
        {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles()
    {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String movieTitle = movies.get(i).getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.indexOf(searchTerm) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort)
    {
        for (int j = 1; j < listToSort.size(); j++)
        {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0)
            {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie)
    {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast()
    {
        System.out.print("Enter a name: ");
        String name = scanner.nextLine();
        name = name.toLowerCase();
        Set<String> c = new HashSet<String>();
        for (int i = 0; i < movies.size(); i++){
            String movieCast = movies.get(i).getCast();
            String movieCastL = movieCast.toLowerCase();
            if (movieCastL.contains(name)){
                String[] arr = movieCast.split("\\|");
                String[] arrL = movieCastL.split("\\|");
                for(int j = 0; j<arr.length;j++){
                    if(arrL[j].contains(name)) c.add(arr[j]);
                }
            }
        }
        ArrayList<String> results = new ArrayList<String>(c);
        for (int j=1;j<results.size();j++){
            String t = results.get(j);
            int p = j;
            while (p>0&&t.compareTo(results.get(p - 1))<0) {
                results.set(p,results.get(p-1));
                p--;
            }
            results.set(p,t);
        }
        for (int i=0;i<results.size();i++){
            String n=results.get(i);
            int choiceNum = i + 1;
            System.out.println(""+choiceNum+". "+n);
        }
        System.out.println("Enter a number to look at a movie: ");
        int num=scanner.nextInt();
        scanner.nextLine();
        String selectedCast = results.get(num - 1);
        ArrayList<Movie> r = new ArrayList<Movie>();
        for (int i = 0; i < movies.size(); i++){
            String movieTitle = movies.get(i).getCast();
            movieTitle = movieTitle.toLowerCase();
            if (movieTitle.indexOf(selectedCast.toLowerCase()) != -1){
                r.add(movies.get(i));
            }
        }
        sortResults(r);
        for (int i = 0; i < r.size(); i++){
            String t = r.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println(""+choiceNum+". "+t);
        }
        System.out.println("Enter a number to look at a movie: ");
        num = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = r.get(num - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords()
    {
        System.out.print("Enter a keyword: ");
        String key = scanner.nextLine();

        // prevent case sensitivity
        key = key.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<Movie>();

        // search through ALL movies in collection
        for (int i = 0; i < movies.size(); i++)
        {
            String word = movies.get(i).getKeywords();
            word = word.toLowerCase();

            if (word.indexOf(key) != -1)
            {
                //add the Movie objest to the results list
                results.add(movies.get(i));
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++)
        {
            String words = results.get(i).getTitle();

            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + words);
        }

        System.out.println("Enter a number to look at a movie: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        try{
            FileReader f = new FileReader("src/Genres");
            BufferedReader b = new BufferedReader(f);
            String line;
            System.out.println("List of genres");
            int i = 0;
            while ((line = b.readLine()) != null){
                System.out.println("" + (i+1)+ ". " + line);
                i++;
            }
            System.out.println("Enter a number to pick a genre: ");
            int c = scanner.nextInt();
            scanner.nextLine();
            b.close();
            f = new FileReader("src/Genres");
            b = new BufferedReader(f);
            for(int k=0;k<c-1;k++){
                b.readLine();
            }
            String gen = b.readLine();
            System.out.println(gen);
            ArrayList<Movie> results = new ArrayList<>();
            for(Movie m: movies){
                if(m.getGenres().contains(gen)) results.add(m);
            }
            sortResults(results);
            for (int j = 0; j < results.size(); j++){
                String title = results.get(j).getTitle();
                int choiceNum = j + 1;
                System.out.println("" + choiceNum + ". " + title);
            }
            System.out.println("Enter a number to look at a movie: ");
            c = scanner.nextInt();
            scanner.nextLine();
            Movie selectedMovie = results.get(c-1);
            displayMovieInfo(selectedMovie);
            System.out.println("\n ** Press Enter to Return to Main Menu **");
            scanner.nextLine();
            b.close();
        }
        catch(IOException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void listHighestRated()
    {
        ArrayList<Movie> results = new ArrayList<>();
        ArrayList<Movie> arr = new ArrayList<>();
        for(Movie m:movies){
            arr.add(m);
        }
        for (int j = 1; j < arr.size(); j++){
            double temp = arr.get(j).getUserRating();
            int possibleIndex = j;
            while (possibleIndex > 0 && temp < arr.get(possibleIndex - 1).getUserRating()){
                Movie t = arr.get(possibleIndex);
                arr.set(possibleIndex,arr.get(possibleIndex-1));
                arr.set(possibleIndex-1,t);
                possibleIndex--;
            }
        }
        for(int i = arr.size() - 1; i>= arr.size()-50;i--) {
            results.add(arr.get(i));
        }
        for (int j = 0; j < results.size(); j++){
            String title = results.get(j).getTitle();
            String rating = "" + results.get(j).getUserRating();
            int choiceNum = j + 1;
            System.out.println(""+choiceNum+". "+title+" rating: "+rating);
        }
        System.out.println("Enter a number to look at a movie: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue()
    {
        ArrayList<Movie> results = new ArrayList<>();
        ArrayList<Movie> arr = new ArrayList<>();
        for(Movie m:movies){
            arr.add(m);
        }
        for (int j = 1; j < arr.size(); j++){
            double temp = arr.get(j).getRevenue();
            int possibleIndex = j;
            while (possibleIndex > 0 && temp < arr.get(possibleIndex - 1).getRevenue()){
                Movie t = arr.get(possibleIndex);
                arr.set(possibleIndex, arr.get(possibleIndex - 1));
                arr.set(possibleIndex - 1, t);
                possibleIndex--;
            }
        }
        for(int i = arr.size() - 1; i>= arr.size()-50;i--){
            results.add(arr.get(i));
        }
        for (int j = 0; j < results.size(); j++){
            String title = results.get(j).getTitle();
            String revenueMov = "$" + results.get(j).getRevenue();
            int choiceNum = j + 1;
            System.out.println("" + choiceNum + ". " + title + " (" + revenueMov + ")");
        }
        System.out.println("Enter a number to look at a movie: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}