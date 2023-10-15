import java.util.ArrayList;
import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class Arbol {
    
    protected ArrayList<TablaSimbolos> r = new ArrayList<TablaSimbolos>();
    protected Nodo n;
    protected Frame fr;
    protected Lista L = new Lista();

    

    public Arbol(ArrayList<TablaSimbolos> R, Nodo N,Frame Fr)
    {
        this.r = R;
        this.n = N;
        this.fr = Fr;
    }

    public Arbol(){

    }

    public ArrayList<TablaSimbolos> getR()
    {
        return r;
    }

    public void setR(ArrayList<TablaSimbolos> a)
    {
        this.r = a;
    }

    public Nodo getN()
    {
        return n;
    }

    public void setN(Nodo a)
    {
        this.n = a;
    }

    public Lista getL()
    {
        return L;
    }

    public void setL(Lista a)
    {
        this.L = a;
    }

    protected void crearAsignación(){

        Stack<Nodo> Simbolos = new Stack<Nodo>(); 
        Stack<Lista> cantidades = new Stack<Lista>(); 

        while(n.getToken() != 125){

            if(n.getToken() == 100 || n.getToken() == 101 || n.getToken() == 102 || n.getToken() == 122 || 
               n.getToken() == 210 ||  n.getToken() == 211){
                
                switch(n.getToken()){
                    case 100:
                        break;
                    case 101:
                        if(n.getLexema().length() > 10)
                        {
                            System.out.print("Error Semantico: Desbordamiento. La variable no puede almacenar mas de 10 digitos");
                            fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                            fr.getLabelSem().setText("----X----");;
                            fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                            fr.getLabelSin().setText("----?----");
                            throw new TerminacionMetodoException(""); 
                        }
                        break;
                    case 102:
                        if(n.getLexema().length() > 10)
                        {
                            fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                            fr.getLabelSem().setText("----X----");;
                            fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                            fr.getLabelSin().setText("----?----");
                            System.out.print("Error Semantico: Desbordamiento. La variable no puede almacenar mas de 10 digitos");
                            throw new TerminacionMetodoException(""); 
                        }
                        break;
                    case 122:
                        if(n.getLexema().length() > 100)
                        {
                            fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                            fr.getLabelSem().setText("----X----");;
                            fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                            fr.getLabelSin().setText("----?----");
                            System.out.print("Error Semantico: Desbordamiento. La variable no puede almacenar mas de 100 caracteres");
                            throw new TerminacionMetodoException(""); 
                        }
                        break;
                    default:
                        break;
                }
                Lista acc = new Lista(n);
                acc.setFr(fr);
                cantidades.push(acc);
                n = n.getUnion();
            }
            else if(n.getToken() == 117) //(
            { 
                Simbolos.push(n); 
                n = n.getUnion(); 
            }
            else if(n.getToken() == 118) //)
            {
                while(!Simbolos.isEmpty() && !(Simbolos.peek().getToken() == 117)) 
                { 
                    Nodo op = Simbolos.pop(); 
                    Lista d2 = cantidades.pop();  
                    d2.setFr(fr);
                    Lista d1 = cantidades.pop(); 
                    d1.setFr(fr);
                    Lista Tope = new Lista(op); 
                    Tope.setFr(fr);
                    //Creamos el nodo 
                    Tope.setIzq(d1); 
                    Tope.setDer(d2); 
                    cantidades.push(Tope); 
                }
                Simbolos.pop();
                n = n.getUnion();
            }
            else
            {
                while(!Simbolos.isEmpty() && prioridad(n) <= prioridad(Simbolos.peek())) 
                { 
                    Nodo op = Simbolos.pop(); 
                    Lista d2 = cantidades.pop();  
                    d2.setFr(fr);
                    Lista d1 = cantidades.pop(); 
                    d1.setFr(fr);
                    Lista Tope = new Lista(op); 
                    Tope.setFr(fr);
 
                    //Creamos el nodo 
                    Tope.setIzq(d1); 
                    Tope.setDer(d2); 
                    cantidades.push(Tope); 
                } 
                Simbolos.push(n); 
                n = n.getUnion();
            }

        }

        while (!Simbolos.isEmpty()) 
        { 
            Nodo op = Simbolos.pop(); 
            Lista d2 = cantidades.pop();  
            d2.setFr(fr);
            Lista d1 = cantidades.pop(); 
            d1.setFr(fr);
            Lista Tope = new Lista(op); 
            Tope.setFr(fr);
 
            //Creamos el nodo 
            Tope.setIzq(d1); 
            Tope.setDer(d2); 
            cantidades.push(Tope); 
        } 
 
        L = cantidades.peek();
        L.setR(r); 

        L.SemanticaPostOrden(L);

        
    }

    protected void crearComparacion(){
        Stack<Nodo> Simbolos = new Stack<Nodo>(); 
        Stack<Lista> cantidades = new Stack<Lista>(); 

        while(n.getToken() != 118){

            if(n.getToken() == 100 || n.getToken() == 101 || n.getToken() == 102 || n.getToken() == 122 || 
               n.getToken() == 210 ||  n.getToken() == 211){
                
                Lista acc = new Lista(n);
                cantidades.push(acc);
                n = n.getUnion();
            }
            else
            {
                while(!Simbolos.isEmpty() && prioridadC(n) <= prioridadC(Simbolos.peek())) 
                { 
                    Nodo op = Simbolos.pop(); 
                    Lista d2 = cantidades.pop();  
                    d2.setFr(fr);
                    Lista d1 = cantidades.pop(); 
                    d1.setFr(fr);
                    Lista Tope = new Lista(op); 
                    Tope.setFr(fr);
 
                    //Creamos el nodo 
                    Tope.setIzq(d1); 
                    Tope.setDer(d2); 
                    cantidades.push(Tope); 
                } 
                Simbolos.push(n); 
                n = n.getUnion();
            }

        }

        while (!Simbolos.isEmpty()) 
        { 
            Nodo op = Simbolos.pop(); 
            Lista d2 = cantidades.pop();  
            d2.setFr(fr);
            Lista d1 = cantidades.pop(); 
            d1.setFr(fr);
            Lista Tope = new Lista(op); 
            Tope.setFr(fr);
 
            //Creamos el nodo 
            Tope.setIzq(d1); 
            Tope.setDer(d2); 
            cantidades.push(Tope); 
        }
        L = cantidades.peek();
        L.setR(r); 
        L.setFr(fr);

        L.SemanticaCPostOrden(L);

    }

    protected int prioridad(Nodo n) 
    { 
        switch(n.getToken()) 
        {
            case 105: 
            case 106: 
                return 2; 
            case 103: 
            case 104: 
                return 1; 
            default: 
                return 0; 
        }
    }

    protected int prioridadC(Nodo n)
    {
        switch(n.getToken()) 
        {
            case 108:
            case 109: 
            case 110: 
            case 111:
            case 112: 
            case 113: 
            default: 
                return 0; 
        }
    }
}

class Lista {

    protected ArrayList<TablaSimbolos> r = new ArrayList<TablaSimbolos>();
    protected Nodo n;
    protected Frame fr;
    protected Lista izq, der;

    //hash para tablas tipos

    Stack<Integer> semantico = new Stack<Integer>();
    int tipo;

    public Lista(Nodo N) {
        this.n = N;
    }

    public Lista(){

    }

    public Frame getFr(){
        return fr;
    }

    public void setFr(Frame a)
    {
        this.fr = a;
    }

    public ArrayList<TablaSimbolos> getR()
    {
        return r;
    }

    public void setR(ArrayList<TablaSimbolos> a)
    {
        this.r = a;
    }

    public Lista getIzq()
    {
        return izq;
    }

    public void setIzq(Lista a)
    {
        this.izq = a;
    }

    public Lista getDer()
    {
        return der;
    }

    public void setDer(Lista a)
    {
        this.der = a;
    }

    protected void imprimirPostorden(Lista a) { 
        if(a != null) 
        { 
            imprimirPostorden(a.getIzq()); 
            imprimirPostorden(a.getDer()); 
            System.out.print(a.n.getLexema() + " "); 
        } 
    }

    protected void SemanticaPostOrden(Lista a){
        if(a != null) 
        { 
            SemanticaPostOrden(a.getIzq());
            SemanticaPostOrden(a.getDer());
            if(a.n.getToken() == 100 || a.n.getToken() == 101 || a.n.getToken() == 102 || a.n.getToken() == 122 || 
               a.n.getToken() == 210 ||  a.n.getToken() == 211 )
               {
                    //token
                    if(a.n.getToken() == 100)
                    {
                        int t = buscarVariable(a.n);
                        semantico.push(t); 
                    }
                    else if(a.n.getToken() == 101){
                        int t = 209;
                        semantico.push(t); 
                    }
                    else if(a.n.getToken() == 102){
                        int t = 208;
                        semantico.push(t); 
                    }
                    else if(a.n.getToken() == 122){
                        int t = 212;
                        semantico.push(t); 
                    }
                    else
                    {
                        int t = 213;
                        semantico.push(t); 
                    }
               }
               else if(a.n.getToken() == 103 || a.n.getToken() == 104 || a.n.getToken() == 105 || a.n.getToken() == 106){

                    int d2 = semantico.pop();
                    int d1 = semantico.pop();
                    tipo = TablaTipos(a.n.getToken(), d1, d2);
                    semantico.push(tipo);
               }
               else
               {
                    int d2 = semantico.pop(); 
                    int d1 = semantico.pop();

                    if(d1 != d2)
                    {
                        System.out.print("Error Semantico: el valor asignado no concuerda con el tipo de la variable");
                        fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                        fr.getLabelSem().setText("----X----");;
                        fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                        fr.getLabelSin().setText("----?----");
                        throw new TerminacionMetodoException(""); 
                    }
               }
        }
    }

    protected void SemanticaCPostOrden(Lista a){
        if(a != null) 
        { 
            SemanticaCPostOrden(a.getIzq());
            SemanticaCPostOrden(a.getDer());
            if(a.n.getToken() == 100 || a.n.getToken() == 101 || a.n.getToken() == 102 || a.n.getToken() == 122 || 
               a.n.getToken() == 210 ||  a.n.getToken() == 211 )
               {
                    //token
                    if(a.n.getToken() == 100)
                    {
                        int t = buscarVariable(a.n);
                        semantico.push(t); 
                    }
                    else if(a.n.getToken() == 101){
                        int t = 209;
                        semantico.push(t); 
                    }
                    else if(a.n.getToken() == 102){
                        int t = 208;
                        semantico.push(t); 
                    }
                    else if(a.n.getToken() == 122){
                        int t = 212;
                        semantico.push(t); 
                    }
                    else
                    {
                        int t = 213;
                        semantico.push(t); 
                    }
               }
               else if(a.n.getToken() == 108 || a.n.getToken() == 109 || a.n.getToken() == 110 || a.n.getToken() == 111 ||
                       a.n.getToken() == 112 || a.n.getToken() == 113){

                    int d2 = semantico.pop();
                    int d1 = semantico.pop();
                    int t = TablaTiposC(a.n.getToken(),d1,d2);
                    semantico.push(t);
               }
               else
               {

                    int d1 = semantico.pop();

                    if(213 != d1)
                    {
                        System.out.print("Error Semantico: el valor asignado no concuerda con el tipo de la variable");
                        fr.getLabelSem().setForeground(new java.awt.Color(247, 36, 36));
                        fr.getLabelSem().setText("----X----");;
                        fr.getLabelSin().setForeground(new java.awt.Color(53, 97, 240));
                        fr.getLabelSin().setText("----?----");
                        throw new TerminacionMetodoException(""); 
                    }
               }
        }
    }
    protected int TablaTipos(int op, int d1, int d2)
    {
        
        Map<Integer, Map<Integer,Integer>> Sum = new HashMap<>();
        Sum.put(209,new HashMap<>());
        Sum.get(209).put(208, 208);
        Sum.get(209).put(209, 209);
        Sum.get(209).put(212, 0);
        Sum.get(209).put(213, 0);

        Sum.put(208,new HashMap<>());
        Sum.get(208).put(208, 208);
        Sum.get(208).put(209, 208);
        Sum.get(208).put(212, 0);
        Sum.get(208).put(213, 0);

        Sum.put(212,new HashMap<>());
        Sum.get(212).put(208, 0);
        Sum.get(212).put(209, 0);
        Sum.get(212).put(212, 212);
        Sum.get(212).put(213, 0);

        Sum.put(213,new HashMap<>());
        Sum.get(213).put(208, 0);
        Sum.get(213).put(209, 0);
        Sum.get(213).put(212, 0);
        Sum.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> res = new HashMap<>();

        res.put(209,new HashMap<>());
        res.get(209).put(208, 208);
        res.get(209).put(209, 209);
        res.get(209).put(212, 0);
        res.get(209).put(213, 0);

        res.put(208,new HashMap<>());
        res.get(208).put(208, 208);
        res.get(208).put(209, 208);
        res.get(208).put(212, 0);
        res.get(208).put(213, 0);

        res.put(212,new HashMap<>());
        res.get(212).put(208, 0);
        res.get(212).put(209, 0);
        res.get(212).put(212, 0);
        res.get(212).put(213, 0);

        res.put(213,new HashMap<>());
        res.get(213).put(208, 0);
        res.get(213).put(209, 0);
        res.get(213).put(212, 0);
        res.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> mul = new HashMap<>();

        mul.put(209,new HashMap<>());
        mul.get(209).put(208, 208);
        mul.get(209).put(209, 209);
        mul.get(209).put(212, 0);
        mul.get(209).put(213, 0);

        mul.put(208,new HashMap<>());
        mul.get(208).put(208, 208);
        mul.get(208).put(209, 208);
        mul.get(208).put(212, 0);
        mul.get(208).put(213, 0);

        mul.put(212,new HashMap<>());
        mul.get(212).put(208, 0);
        mul.get(212).put(209, 0);
        mul.get(212).put(212, 0);
        mul.get(212).put(213, 0);

        mul.put(213,new HashMap<>());
        mul.get(213).put(208, 0);
        mul.get(213).put(209, 0);
        mul.get(213).put(212, 0);
        mul.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> div = new HashMap<>();

        div.put(209,new HashMap<>());
        div.get(209).put(208, 208);
        div.get(209).put(209, 208);
        div.get(209).put(212, 0);
        div.get(209).put(213, 0);

        div.put(208,new HashMap<>());
        div.get(208).put(208, 208);
        div.get(208).put(209, 208);
        div.get(208).put(212, 0);
        div.get(208).put(213, 0);

        div.put(212,new HashMap<>());
        div.get(212).put(208, 0);
        div.get(212).put(209, 0);
        div.get(212).put(212, 0);
        div.get(212).put(213, 0);

        div.put(213,new HashMap<>());
        div.get(213).put(208, 0);
        div.get(213).put(209, 0);
        div.get(213).put(212, 0);
        div.get(213).put(213, 0);

        switch(op){
            case 103: // +
                if(Sum.containsKey(d1) && Sum.get(d1).containsKey(d2))
                {
                    return Sum.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 104: // -
                if(res.containsKey(d1) && res.get(d1).containsKey(d2))
                {
                    return Sum.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 105: // * 
                if(mul.containsKey(d1) && mul.get(d1).containsKey(d2))
                {
                    return mul.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 106: // /
                if(div.containsKey(d1) && div.get(d1).containsKey(d2))
                {
                    return div.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }   
            default:
                return 1;
        }


    }

    protected int TablaTiposC(int op, int d1, int d2)
    {
        Map<Integer, Map<Integer,Integer>> menor = new HashMap<>();
        menor.put(209,new HashMap<>());
        menor.get(209).put(208, 213);
        menor.get(209).put(209, 213);
        menor.get(209).put(212, 0);
        menor.get(209).put(213, 0);

        menor.put(208,new HashMap<>());
        menor.get(208).put(208, 213);
        menor.get(208).put(209, 213);
        menor.get(208).put(212, 0);
        menor.get(208).put(213, 0);

        menor.put(212,new HashMap<>());
        menor.get(212).put(208, 0);
        menor.get(212).put(209, 0);
        menor.get(212).put(212, 0);
        menor.get(212).put(213, 0);

        menor.put(213,new HashMap<>());
        menor.get(213).put(208, 0);
        menor.get(213).put(209, 0);
        menor.get(213).put(212, 0);
        menor.get(213).put(213, 0);

        Map<Integer, Map<Integer,Integer>> mayor= new HashMap<>();
        mayor.put(209, new HashMap<>());
        mayor.get(209).put(208, 213);
        mayor.get(209).put(209, 213);
        mayor.get(209).put(212, 0);
        mayor.get(209).put(213, 0);

        mayor.put(208, new HashMap<>());
        mayor.get(208).put(208, 213);
        mayor.get(208).put(209, 213);
        mayor.get(208).put(212, 0);
        mayor.get(208).put(213, 0);

        mayor.put(212, new HashMap<>());
        mayor.get(212).put(208, 0);
        mayor.get(212).put(209, 0);
        mayor.get(212).put(212, 0);
        mayor.get(212).put(213, 0);

        mayor.put(213, new HashMap<>());
        mayor.get(213).put(208, 0);
        mayor.get(213).put(209, 0);
        mayor.get(213).put(212, 0);
        mayor.get(213).put(213, 0);


        Map<Integer,Map<Integer,Integer>> menorI = new HashMap<>();

        menorI.put(209, new HashMap<>());
        menorI.get(209).put(208, 213);
        menorI.get(209).put(209, 213);
        menorI.get(209).put(212, 0);
        menorI.get(209).put(213, 0);

        menorI.put(208, new HashMap<>());
        menorI.get(208).put(208, 213);
        menorI.get(208).put(209, 213);
        menorI.get(208).put(212, 0);
        menorI.get(208).put(213, 0);

        menorI.put(212, new HashMap<>());
        menorI.get(212).put(208, 0);
        menorI.get(212).put(209, 0);
        menorI.get(212).put(212, 0);
        menorI.get(212).put(213, 0);

        menorI.put(213, new HashMap<>());
        menorI.get(213).put(208, 0);
        menorI.get(213).put(209, 0);
        menorI.get(213).put(212, 0);
        menorI.get(213).put(213, 0);


        Map<Integer,Map<Integer,Integer>> mayorI = new HashMap<>();

        mayorI.put(209, new HashMap<>());
        mayorI.get(209).put(208, 213);
        mayorI.get(209).put(209, 213);
        mayorI.get(209).put(212, 0);
        mayorI.get(209).put(213, 0);

        mayorI.put(208, new HashMap<>());
        mayorI.get(208).put(208, 213);
        mayorI.get(208).put(209, 213);
        mayorI.get(208).put(212, 0);
        mayorI.get(208).put(213, 0);

        mayorI.put(212, new HashMap<>());
        mayorI.get(212).put(208, 0);
        mayorI.get(212).put(209, 0);
        mayorI.get(212).put(212, 0);
        mayorI.get(212).put(213, 0);

        mayorI.put(213, new HashMap<>());
        mayorI.get(213).put(208, 0);
        mayorI.get(213).put(209, 0);
        mayorI.get(213).put(212, 0);
        mayorI.get(213).put(213, 0);

        Map<Integer,Map<Integer,Integer>> diff = new HashMap<>();

        diff.put(209, new HashMap<>());
        diff.get(209).put(208, 213);
        diff.get(209).put(209, 213);
        diff.get(209).put(212, 0);
        diff.get(209).put(213, 0);

        diff.put(208, new HashMap<>());
        diff.get(208).put(208, 213);
        diff.get(208).put(209, 213);
        diff.get(208).put(212, 0);
        diff.get(208).put(213, 0);

        diff.put(212, new HashMap<>());
        diff.get(212).put(208, 0);
        diff.get(212).put(209, 0);
        diff.get(212).put(212, 213);
        diff.get(212).put(213, 0);

        diff.put(213, new HashMap<>());
        diff.get(213).put(208, 0);
        diff.get(213).put(209, 0);
        diff.get(213).put(212, 0);
        diff.get(213).put(213, 0);

        Map<Integer, Map<Integer, Integer>> same = new HashMap<>();

        same.put(209, new HashMap<>());
        same.get(209).put(208, 213);
        same.get(209).put(209, 213);
        same.get(209).put(212, 0);
        same.get(209).put(213, 0);

        same.put(208, new HashMap<>());
        same.get(208).put(208, 213);
        same.get(208).put(209, 213);
        same.get(208).put(212, 0);
        same.get(208).put(213, 0);

        same.put(212, new HashMap<>());
        same.get(212).put(208, 0);
        same.get(212).put(209, 0);
        same.get(212).put(212, 213);
        same.get(212).put(213, 0);

        same.put(213, new HashMap<>());
        same.get(213).put(208, 0);
        same.get(213).put(209, 0);
        same.get(213).put(212, 0);
        same.get(213).put(213, 0);



        switch(op){
            case 108: // <
                if(menor.containsKey(d1) && menor.get(d1).containsKey(d2))
                {
                    return menor.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 109: // >
                if(mayor.containsKey(d1) && mayor.get(d1).containsKey(d2))
                {
                    return mayor.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 110: // <=
                if(menorI.containsKey(d1) && menorI.get(d1).containsKey(d2))
                {
                    return menorI.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 111: // >=
                if(mayorI.containsKey(d1) && mayorI.get(d1).containsKey(d2))
                {
                    return mayorI.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 112: //==
                if(same.containsKey(d1) && same.get(d1).containsKey(d2))
                {
                    return same.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            case 113: // !=
                if(diff.containsKey(d1) && diff.get(d1).containsKey(d2))
                {
                    return diff.get(d1).get(d2);
                }
                else
                {
                    return 0;
                }
            default:
                return 1;
        }
    }

    public int buscarVariable(Nodo p){
        int a = p.getToken();
        for(TablaSimbolos  b : r)
            {
                if(p.getLexema().equals(b.getId()))
                {
                   a = b.getToken();
                }
                    
            }
        return a;
    }
}

class TerminacionMetodoException extends RuntimeException {
    public TerminacionMetodoException(String mensaje) {
        super(mensaje);
    }
}