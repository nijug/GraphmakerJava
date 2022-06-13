package com.example.graphmaker;
import java.io.*;
import java.util.*;


public class Graph {
    private int x;
    private int y;
    private double randB;
    private double randE;
    private int n;
    private ArrayList<LinkedList<Node>> graph; // graph zapisany tak samo jak w c

    private Queue<Integer> current = new LinkedList<Integer>();
    private Queue<Integer> old = new LinkedList<Integer>();
    private PriorityQueue<Box> awaiting = new PriorityQueue<>();
    private Queue<Box> visited =  new LinkedList<>();

   public double findValue(LinkedList<Node> temp, int point) { // szukanie value, pierwszy argumet tablica sąsiadów, druga nr sąsiada
       for (int i= 0; i< temp.size(); i++) {
           if (temp.get(i).getPoint()==point) {
               return temp.get(i).getValue();
           }
       }
    return -1;
   }

    public void generate(int x, int y,double randB, double randE) {
        this.x=x;
        this.y=y;
        this.randB=randB;
        this.randE=randE;
        this.graph = new ArrayList<LinkedList<Node>>(this.x * this.y);
        for (int i = 0; i < this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                this.graph.add(new LinkedList<Node>()); // dodaje nowa liste
                if (i != 0) { // górny
                    this.graph.get(i * this.y + j).add(new Node((i - 1) * this.y + j, findValue(this.graph.get((i - 1) * this.y + j), i * this.y + j)));
                }
                if (j + 1 != y) { //prawy
                    this.graph.get(i * this.y + j).add(new Node((i * this.y + j + 1), Double.parseDouble(String.format(Locale.US,"%.6f",Math.random()*(randE-randB)+randB ))));// maksymalnie 6 liczb po przecinku
                }
                if (j != 0) // lewy
                    this.graph.get(i * this.y + j).add(new Node(i * this.y + j - 1, findValue(this.graph.get(i * this.y + j - 1), i * y + j)));
                if (i + 1 != x) { //dolny
                    this.graph.get(i * this.y + j).add(new Node((i + 1) * this.y + j,  Double.parseDouble(String.format(Locale.US,"%.6f",Math.random()*(randE-randB)+randB ))));
                }
            }
        }
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

    public LinkedList<Node> getList(int index) { //potrzebne do użycia getValue poza klasą graph
       return this.graph.get(index);
    }
    public void save(String content, File file) { // zapisuje do pliku, content to graph.toString, ifile to wybrany plik z file chooser
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            View.displayAlert("Błąd podczas zapisywania pliku");
        }
    }

    public void open(File file) { //scanner czyta plik
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            scanner.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            View.displayAlert("Błąd podczas otwierania pliku");
        }
        try {
            this.x = scanner.nextInt();
            this.y = scanner.nextInt();
            scanner.nextLine();
            this.graph = new ArrayList<LinkedList<Node>>(this.x * this.y);
            Scanner line;
            for (int i = 0; i < this.x; i++) {
                for (int j = 0; j < this.y; j++) {
                    this.graph.add(new LinkedList<Node>());
                    line = new Scanner(scanner.nextLine());
                    while (line.hasNext()) {
                        this.graph.get(i * this.y + j).add(new Node(Integer.parseInt(line.next()), Double.parseDouble(line.next())));
                    }
                }
            }
        } catch (NoSuchElementException | NullPointerException | IllegalArgumentException e) {
            View.displayAlert("Nieprawidłowy format pliku");
        }
   }


    @Override
    public String toString() { // do debugowania
        StringBuilder sb = new StringBuilder(String.format("%d %d \n", this.x, this.y));
        for (int i = 0; i <this.x; i++) {
            for (int j = 0; j < this.y; j++) {
                for (int k = 0; k < this.graph.get(i * this.y + j).size(); k++) {
                    sb.append(String.format(Locale.US, "%d %f ", this.graph.get(i * this.y + j).get(k).getPoint(), this.graph.get(i * this.y + j).get(k).getValue())); // locale us po to żeby liczby były z . a nie z ,
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    double dijkstra(int s, int b, int x, int y){
        int size, result = bfs(s, b, x, y), curr_node = 0;
        if(result == 0)
            return -1;
        if(result == 2)
            return -2;
        if(result == 3)
            return -3;

        size = old.size();
        double dist = 222222;
        int curr = s, i, j, k, l;

        Box crafted = null;

        Box ptr = new Box(s, 0);
        awaiting.add(ptr);
        ptr.setPrev(awaiting.peek());
        //box_2 *ptr = first_l;
        //first_l_2 = first_l;


        Node tmp;

        // petla powtarza sie tyle razy ile wezlow bfs znalazl w tej czesci grafu
        for(k = 0; k < size; k++){
            curr_node = 0;
            i = curr / y;
            j = curr % y;

            //tmp = graph[curr];

            tmp = this.graph.get(curr).get(curr_node);
            if(tmp != null){
                l = this.graph.get(curr).size() - 1;

                if(i != 0){ // gorny - rozpatrywanie krawedzi idacej w gore

                    if(tmp.value != -1.0)
                    {
                        // sprawdzenie czy wskazywany wezel jest juz w liscie wezlow
                        // (tej zawierajacej wezly rozpatrzone i nie)
                        crafted = is_listed(tmp.point);
                        // przypadek gdy wskazywany wezel juz znajduje sie w liscie
                        if(crafted != null)
                        {
                            // jesli droga do wskazywanego wezla jest dluzsza od obecnie rozpatrywanej drogi
                            // to nastepuje zamiana wartosci jej dlugosci oraz poprzednika wskazywanego wezla
                            if (crafted.getLength() > (ptr.getLength() + tmp.value))
                            {
                                crafted.setLength(ptr.getLength() + tmp.value);
                                crafted.setPrev(ptr);
                            }
                        }
                        else
                        {
                            // jesli wskazywany wezel nie byl wczesniej dodany do listy,
                            // to zostaje on dodany, i jako poprzednika w najkrotszej drodze od zrodla
                            // wskazuje obecnie rozpatrywany wezel
                            crafted = new Box(tmp.point, ptr.getLength() + tmp.value);
                            awaiting.add(crafted);
                            crafted.setPrev(ptr);
                        }

                    }
                    if(curr_node<l) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

                if(j+1 != y){ // prawy - rozpatrywanie krawedzi idacej w prawo
                    if(tmp.value != -1.0)
                    {
                        crafted = is_listed(tmp.point);
                        if(crafted != null)
                        {
                            if (crafted.getLength() > (ptr.getLength() + tmp.value))
                            {
                                crafted.setLength(ptr.getLength() + tmp.value);
                                crafted.setPrev(ptr);
                            }
                        }
                        else
                        {
                            crafted = new Box(tmp.point, (ptr.getLength() + tmp.value));
                            awaiting.add(crafted);
                            crafted.setPrev(ptr);
                        }
                    }
                    if(curr_node<l) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

                if(j != 0){ // lewy - rozpatrywanie krawedzi idacej w prawo
                    if(tmp.value != -1.0)
                    {
                        crafted = is_listed(tmp.point);
                        if(crafted != null)
                        {
                            if (crafted.getLength() > (ptr.getLength() + tmp.value))
                            {
                                crafted.setLength(ptr.getLength() + tmp.value);
                                crafted.setPrev(ptr);
                            }
                        }
                        else
                        {
                            crafted = new Box(tmp.point, (ptr.getLength() + tmp.value));
                            awaiting.add(crafted);
                            crafted.setPrev(ptr);
                        }
                    }
                    if(curr_node<l) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

                if(i+1 != x){ // dolny - rozpatrywanie krawedzi idacej w dol

                    if(tmp.value != -1.0)
                    {
                        crafted = is_listed(tmp.point);
                        if(crafted != null)
                        {
                            if (crafted.getLength() > (ptr.getLength() + tmp.value))
                            {
                                crafted.setLength(ptr.getLength() + tmp.value);
                                crafted.setPrev(ptr);
                            }
                        }
                        else
                        {
                            crafted = new Box(tmp.point, (ptr.getLength() + tmp.value));
                            awaiting.add(crafted);
                            crafted.setPrev(ptr);
                        }
                    }
                    if(curr_node<l) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

            }

            visited.add(awaiting.poll());
            ptr = awaiting.peek();

            // zamiana numeru obecnego wezla na numer pierwszego wezla w kolejce do rozpatrzenia (priorytetowej)
            if(ptr!=null)
                curr = ptr.getNumber();
            if(curr==b){
                visited.add(awaiting.poll());
                break;
            }

        }
        // jesli szukamy najkrotzej sciezki miedzy dwoma wezlami i algorytm bfs wykazal,
        // ze przynajmniej jedna taka sciezka istnieje, to ta metoda zwraca jej dlugosc

        ptr = is_listed(b);
        if(ptr != null)
            dist = ptr.getLength();
        System.out.printf("Najkrotsza sciezka miedzy punktami \"%d\" i \"%d\" ma dlugosc: %f\n", s, b, dist);
        //show_path(b);
        return dist;
    }

    Box is_listed(int nb)
    {
        if(visited.peek()!=null)
        {
            //Box tmp = this.graph.get(curr);
            Box le = null;
            for(Box el : this.visited)
            {
                //if (this.awaiting.get(k).getNumber() == nb)
                if(el.getNumber() == nb)
                    return el;
                //this.awaiting.get(k);
                le = el;
            }
            if (le.getNumber()==nb)
                return le;
            //this.awaiting.get(k).getNumber() == nb)
                //return this.awaiting.get(k);
        }
        if(awaiting.peek()!=null){
            Box le = null;
            for(Box el : this.awaiting)
            {
                //if (this.awaiting.get(k).getNumber() == nb)
                if(el.getNumber() == nb)
                    return el;
                //this.awaiting.get(k);
                le = el;
            }
            if (le.getNumber()==nb)
                return le;
        }
        return null;
    }

    // Tworze dwie kolejki
    // current, old
    public int bfs(int a, int b, int x, int y){
        int curr = a, found = 0, i, j, curr_node, k;

        current.add(curr);
        // enqueue_curr(curr);

        Node tmp;
        while(current.peek()!=null){
            curr_node = 0;
            if(curr == b) found = 1;
            // wczesniej rozwiazywane to bylo poprzez uzywanie wskaznika next,
            // teraz jednak numer krawedzi jest zawarty w tej zmiennej

            i = curr / y;
            j = curr % y;
            tmp = this.graph.get(curr).get(curr_node);
            if(tmp != null){
                k = this.graph.get(curr).size() - 1;
                if(i!=0){ // gorny
                    if(tmp.value!=-1.0)
                        // jesli dany numer wezla nie pojawil sie jeszcze w liscie rozpatrzonych oraz w liscie do rozpatrzenia
                        // to zostaje on dodany do listy wezlow do rozpatrzenia
                        if((current.contains(tmp.point)!=true)&&(old.contains(tmp.point)!=true))
                            current.add(tmp.point);
                    if(curr_node<k) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

                if(j+1!=y){ // lewy
                    if(tmp.value!=-1.0)
                        if((current.contains(tmp.point)!=true)&&(old.contains(tmp.point)!=true))
                            current.add(tmp.point);
                    if(curr_node<k) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

                if(j!=0){ // prawy
                    if(tmp.value!=-1.0)
                        if((current.contains(tmp.point)!=true)&&(old.contains(tmp.point)!=true))
                            current.add(tmp.point);
                    if(curr_node<k) {
                        curr_node++;
                        tmp = this.graph.get(curr).get(curr_node);
                    }
                }

                if(i+1!=x){ //dolny
                    if(tmp.value!=-1.0)
                        if((current.contains(tmp.point)!=true)&&(old.contains(tmp.point)!=true))
                            current.add(tmp.point);
                }
            }

            // zapisanie obecnego wezla do kolejki rozpatrzonych
            // usuniecie obecnego wezla z kolejki do rozpatrzenia
            old.add(current.poll());


            // sytuacja, gdy kolejka do rozpotrzenia jest pusta
            // (wszystkie dostepne wezly zostaly rozpatrzone)
            if(current.peek() == null)
            {
                // sytuacja, gdy sprawdzamy spojnosc calego grafu
                if(a == b)
                {
                    // if(queue_size() == (x*y))
                    if(old.size() == (x*y))
                    {
                        //clear_queue_curr();
                        //clear_queue();
                        System.out.printf("Graf jest spojny.%n");
                        return 2;
                    }
                    else
                    {
                        //clear_queue_curr();
                        //clear_queue();
                        System.out.printf("Graf nie jest spojny.%n");
                        return 3;
                    }
                }
                // sytuacja, gdy sprawdzamy tylko czy istnieje
                // sciezka miedzy dwoma konkretnymi wezlami
                else
                {
                    if(found == 0)
                    {
                        //clear_queue_curr();
                        //clear_queue();
                        System.out.printf("Sciezka miedzy wezlami %d i %d nie istnieje!n",a,b);
                    }
                    return found;
                }
            }

            // zamiana numeru obecnego wezla na numer pierwszego wezla w kolejce do rozpatrzenia
            // curr = (first_2->nb);
            curr = current.peek();
        }
        return 0;
    }
}
