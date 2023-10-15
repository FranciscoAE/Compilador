import java.util.ArrayList;

public class Sintatico {

        //Sintactico
        Nodo p;
        Frame fr;
         
        //Para semantico
        String TipoDato, Linea, Id;
        int token;
        ArrayList<TablaSimbolos>  a = new ArrayList<TablaSimbolos>();
        ArrayList<Nodo> n = new ArrayList<Nodo>();
        TablaSimbolos R;
        
        //Constructor
        public Sintatico(Nodo nodo, Frame Fr)
        {
            p = nodo;
            fr = Fr;
        }
        
          String[][] Errores =
        {
            {"Se esperaba la palabra: main",                                                                       "1"},
            {"Se esperaba una apertura de parentesis:: (" ,                                               "2"},
            {"Se esperaba un cierre de parentesis: ) ",                                                       "3"},
            {"Se esperaba una apertura de llave: {",                                                            "4"},
            {"Se esperaba un cierre de llave: } ",                                                                 "5"},
            {"Se esperaba un identificador: id",                                                                   "6"},
            {"Se esperaba una palabra reservada: if /while/print/id",                                "7"},
            {"Se esperaba un cierre de instruccion: ;",                                                       "8"},
            {"Se esperaba la declaracion de alguna variable: bool/int/float/string",        "9"},
            {"Se esperaba la declaracion de alguna sentencia",                                    "10"},
            {"Se esperaba el simbolo igualitario: =",                                                        "11"},
            {"Se esperaba un operador aditivo",                                                              "12"},
            {"Se esperaba un operador multiplicativo",                                                    "13"},
            {"Se esperaba la declaracion de un signo",                                                   "14"},
            {"Se esperaba la declaracion de una expresion relacional",                       "15"},
            {"Se esperaba un signo de exclamacion: !",                                                  "16"},
            {"Se espera un cierre de cadena",                                                                  "17"},
            {"Se espera el uso de una coma: , ",                                                               "18"},
            {"Se esperaba la palabra reservada: new ",                                                  "19"},
            {"Se espera un identificador(id) o una cadena ",                                           "20"},
            {"Se esperaba un valor de asignación:",                                                        "21"},
            {"La variable NO esta declarada      ",                                                        "22"},
            {"La variable YA esta declarada      ",                                                        "23"}
        }; 
          
          public void pawn()
        {
            while(p != null)
            {
                if(p != null && ( p.getToken() == 203))
                {
                    p  = p.getUnion();
                    if(p != null && (p.getToken() == 117))
                    {
                        p  = p.getUnion();
                        if(p != null && (p.getToken() == 118))
                        {
                            p  = p.getUnion();
                            if(p != null &&(p.getToken() == 119))
                            {
                                p = p.getUnion();
                                while(p != null && (p.getToken() == 207))
                                    {
                                        variable();
                                    }
                                if(p != null && (p.getToken() == 201 || p.getToken() == 204 || p.getToken() == 206 || p.getToken() == 100))
                                {
                                    while(p != null && (p.getToken() != 120))
                                    {
                                        statement();
                                    }
                                    if(p != null && (p.getToken() == 120))
                                    {
                                        p = p.getUnion();
                                    }
                                    else
                                    {
                                        ImprimirError(5);
                                        ErrorSintatico();
                                        throw new TerminacionMetodoException("");
                                    }
                                }
                                else
                                {
                                      if(p != null && (p.getToken() == 120) )
                                      {
                                         p = p.getUnion();
                                      }
                                      else
                                      {
                                         ImprimirError(19);
                                         ErrorSintatico();
                                         throw new TerminacionMetodoException("");
                                      }
                                }
                            }
                            else
                            {
                                ImprimirError(4);
                                ErrorSintatico();
                                throw new TerminacionMetodoException("");
                            }
                        }
                        else
                        {
                            ImprimirError(3);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        ImprimirError(2);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                ImprimirError(1);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
                }
            }
            System.out.println("Analizador Sintatico Terminado.");
           for (TablaSimbolos  b :  a) {
                System.out.println(b.getDato() + " " + b.getId() + " " + b.getRenglon() + " " + b.getToken());
           }
        }

    public void variable(){
        if(p != null &&(p.getToken() == 207)){
            p = p.getUnion();
            tipos();
            if(p != null && (p.getToken() == 100)) {
                Id = p.getLexema();
                Linea = String.valueOf(p.getRenglon());
                ValidarIgualdad(a);
                R = new TablaSimbolos(TipoDato,token, Id, Linea);
                a.add(R);
                p = p.getUnion();
                if(p != null && (p.getToken() == 124)){
                    variableRecursividad();
                    if(p != null &&(p.getToken() == 125)){
                        p = p.getUnion();
                    }
                    else{
                        ImprimirError(8);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else {
                    if(p != null &&(p.getToken() == 125)){
                        p = p.getUnion();
                    }
                    else{
                        ImprimirError(8);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
            }
            else {
                ImprimirError(6);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }
        else {
            ImprimirError(19);
            ErrorSintatico();
           throw new TerminacionMetodoException("");
        }
    }
    
    public void variableRecursividad()
        {
            if(p != null &&(p.getToken() == 124))
            {
                p = p.getUnion();
                if(p != null &&(p.getToken() == 100))
                {
                    Id = p.getLexema();
                    Linea = String.valueOf(p.getRenglon());
                    ValidarIgualdad(a);
                    TablaSimbolos R = new TablaSimbolos(TipoDato,token, Id, Linea);
                    a.add(R);
                    p = p.getUnion();
                    if(p != null &&(p.getToken() == 124))
                    {
                        variableRecursividad();
                    }
 
                }
                else
                {
                    ImprimirError(6);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
            }
            else
            {
                ImprimirError(18);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }

    public void statement()
    {
        switch (p.getToken()) {
            case 201:
                //if - else
                p = p.getUnion();
                if(p != null &&(p.getToken() == 117))
                {
                    p = p.getUnion();
                    Arbol arb = new Arbol(a,p,fr);
                    exp_Cond();
                    if(p != null &&(p.getToken() == 118))
                    {
                        arb.crearComparacion();
                        p = p.getUnion();
                        if(p != null &&(p.getToken() == 119))
                        {
                            p = p.getUnion();
                            while(p.getToken() != 120 && p!= null)
                            {
                                statement();
                            }
                            if(p != null &&(p.getToken() == 120))
                            {
                                p = p.getUnion();
                                if(p != null &&(p.getToken() == 202))
                                {
                                    p = p.getUnion();
                                    if(p != null &&(p.getToken() == 119))
                                    {
                                        p = p.getUnion();
                                        while(p.getToken() != 120 || p != null)
                                        {
                                            statement();
                                        }
                                        if(p != null &&(p.getToken() == 120))
                                        {
                                            p = p.getUnion();
                                        }
                                        else
                                        {
                                            ImprimirError(5);
                                            ErrorSintatico();
                                            throw new TerminacionMetodoException("");
                                        }
                                    }
                                    else
                                    {
                                      ImprimirError(4);
                                      ErrorSintatico();
                                      throw new TerminacionMetodoException("");
                                    }
                                }
                                
                            }
                            else
                            {
                                ImprimirError(5);
                                ErrorSintatico();
                                throw new TerminacionMetodoException("");
                            }
                        }
                        else
                        {
                            ImprimirError(4);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                        ImprimirError(3);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                        ImprimirError(2);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                }
                break;
            case 204:
                //while
                p = p.getUnion();
                if(p != null &&(p.getToken() == 117))
                {
                    p = p.getUnion();
                    Arbol arb = new Arbol(a,p,fr);
                    exp_Cond();
                    if(p != null &&(p.getToken() == 118))
                    {
                        arb.crearComparacion();
                        p = p.getUnion();
                        if(p != null &&(p.getToken() == 119))
                        {
                            p = p.getUnion();
                            while(p.getToken() != 120 && p.getUnion() != null)
                            {
                                statement();
                            }
                            if(p != null &&(p.getToken() == 120))
                            {
                                p = p.getUnion();
                            }
                            else
                            {
                                 ImprimirError(5);
                                 ErrorSintatico();
                                 throw new TerminacionMetodoException("");
                            }
                        }
                        else
                        {
                            ImprimirError(4);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                        }
                    }
                    else
                    {
                         ImprimirError(3);
                         ErrorSintatico();
                         throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(2);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
            case 206:
                //print
                p = p.getUnion();
                if(p != null &&(p.getToken() == 117))
                {
                    p = p.getUnion();
                    if(p != null &&(p.getToken()==100 || p.getToken() == 122))
                    {
                        ValidarExistencia(a);
                        p = p.getUnion();
                            if(p != null &&(p.getToken() == 124))
                            {
                                PrintRecursividad();
                                if(p != null &&(p.getToken() == 118))
                                {
                                    p = p.getUnion();
                                   if(p != null &&(p.getToken() == 125))
                                    {
                                        p = p.getUnion();
                                    }
                                   else
                                   {
                                       ImprimirError(8);
                                       ErrorSintatico();
                                       throw new TerminacionMetodoException("");
                                   }
                                }
                                else
                                {
                                    ImprimirError(3);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                }
                            }
                            else
                            {
                                if(p != null &&(p.getToken() == 118))
                                {
                                    p = p.getUnion();
                                    if(p != null &&(p.getToken() == 125))
                                    {
                                        p = p.getUnion();
                                    }
                                    else
                                    {
                                        ImprimirError(8);
                                        ErrorSintatico();
                                        throw new TerminacionMetodoException("");
                                    }
                                }
                                else
                                {
                                    ImprimirError(3);
                                    ErrorSintatico();
                                    throw new TerminacionMetodoException("");
                                }
                            }
                    }
                    else
                    {
                        ImprimirError(6);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(2);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
                //Asignación de variable
            case 100:
                ValidarExistencia(a);
                Arbol arb = new Arbol(a,p,fr);
                p = p.getUnion();
                if(p != null &&(p.getToken() ==  123))
                {
                    p = p.getUnion();
                    //getvalue
                    if(p != null && (p.getToken() == 214))
                    {
                         p = p.getUnion();
                         if(p!=null &&(p.getToken () == 117))
                         {
                             p = p.getUnion();
                             if(p!=null &&(p.getToken() == 118))
                             {
                                p = p.getUnion();
                             }
                             else
                             {
                                 ImprimirError(3);
                                 ErrorSintatico();
                                 throw new TerminacionMetodoException("");
                             }
                         }
                         else
                         {
                            ImprimirError(2);
                            ErrorSintatico();
                            throw new TerminacionMetodoException("");
                         }
                    }
                    else
                    {
                        exp_simp();
                    }
                    if(p != null &&(p.getToken() == 125))
                    {
                        //Aqui ira lo de Arbol
                        arb.crearAsignación();
                        p = p.getUnion();
                    }
                    else
                    {
                        ImprimirError(8);
                        ErrorSintatico();
                        throw new TerminacionMetodoException("");
                    }
                }
                else
                {
                    ImprimirError(11);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
                break;
            default:
                break;
        }
    }
    
    public void PrintRecursividad()
        {
            if(p != null &&(p.getToken() == 124))
            {
                p = p.getUnion();
                if(p != null &&(p.getToken() == 100 || p.getToken() == 122))
                {
                    ValidarExistencia(a);
                    p = p.getUnion();
                    if(p != null &&(p.getToken() == 124))
                    {
                        PrintRecursividad();
                    }
 
                }
                else
                {
                    ImprimirError(20);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
                }
            }
            else
            {
                ImprimirError(18);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }
    
    public void tipos()
        {
            switch(p.getToken())
            {
                case 208:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                     break;
                case 209:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                case 212:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                case 213:
                    TipoDato = (p.getLexema());
                    token = (p.getToken());
                    p = p.getUnion();
                    break;
                default:
                    ImprimirError(9);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
            }
        }
    public void exp_Cond() 
        {
            //aqui comienza
            Arbol arb = new Arbol(a,p,fr);
            exp_simp();
            exp_relac();
            exp_simp();
        }
    
    public void exp_simp() 
    {
             termino();
            
            if(p != null &&(p.getToken() == 103 || p.getToken() == 104 || p.getToken() == 115))
            {
               op_aditivo();
               exp_simp();
            }
            
      }
     private void termino() 
        {
           factor();
           if(p != null &&(p.getToken() == 105 || p.getToken() == 106 ||  p.getToken() == 114 ))
           {
               op_mult();
               termino();
           }
        }
     
     private void factor()
     {
         switch(p.getToken())
         {
             case 100:
                ValidarExistencia(a);
                p = p.getUnion();
                break;
              case 101:
                  p = p.getUnion();
                 break;
              case 102:
                  p = p.getUnion();
                 break;
              case 122:
                  p = p.getUnion();
                 break;
              case 211:
                  p = p.getUnion();
                 break;
              case 210:
                  p = p.getUnion();
                 break;
              case 116:
                  p = p.getUnion();
                  factor();
                 break;
              case 103:
                  signo();
                  factor();
                 break;
              case 104:
                  signo();
                  factor();
                 break;
              case 117:
                  p = p.getUnion();
                  exp_simp();
                   if(p != null &&(p.getToken() == 118))
                   {
                       p = p.getUnion();
                   }
                   else
                   {
                       ImprimirError(3);
                       ErrorSintatico();
                       throw new TerminacionMetodoException("");
                   }
                  break;
                default:
                    ImprimirError(21);
                    ErrorSintatico();
                    throw new TerminacionMetodoException("");
         }
     }
    

     
    public void exp_relac()
    {
       switch(p.getToken())
       {
           case 108:
               p = p.getUnion();
               break;
           case 109:
               p = p.getUnion();
               break;
           case 110:
               p = p.getUnion();
               break;
           case 111:
               p = p.getUnion();
               break;
           case 112:
               p = p.getUnion();
               break;
           case 113:
               p = p.getUnion();
               break;
           default:
                ImprimirError(15);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
       }
       
       }
    
    private void signo() 
    {
        if(p != null &&(p.getToken() == 103 || p.getToken() == 104))
        {
            p = p.getUnion();

        }
            else
            {
                ImprimirError(14);
                ErrorSintatico();
                throw new TerminacionMetodoException("");
            }
        }
   
        public void op_mult()
        {
            if(p != null &&(p.getToken() == 105 || p.getToken() ==106 || p.getToken() ==114))
            {
               p =p.getUnion();
            }
            else
            {
               ImprimirError(13);
               ErrorSintatico();
               throw new TerminacionMetodoException("");
            }
        }

    
       public void op_aditivo()
       {
          
           if(p != null &&(p.getToken()== 103 || p.getToken()==104 || p.getToken()==115))
           {
                p = p.getUnion();
           }
          
           else
          {
              ImprimirError(12);
              ErrorSintatico();
              throw new TerminacionMetodoException("");
          }
      }
        
        

        
        public void ImprimirError(int bandError)
        {
            for(String[] Error : Errores)
            {
                if(bandError == (Integer.valueOf(Error[1])))
                {
                    if(p != null){
                        System.out.println("El error es:  " + Error[0] + " en la linea: " + p.getRenglon());
                    }
                    else
                    {
                        System.out.println("El error es:  " + Error[0] );
                    }
                }
            }
        }

        public void ValidarExistencia(ArrayList<TablaSimbolos> a)
        {
            for(TablaSimbolos b : a)
            {
                if(p.getLexema().equals(b.getId()))
                {
                    return;
                }
            }
            ImprimirError(22);
            ErrorSintatico();
            throw new TerminacionMetodoException(""); 

        }


        public void ValidarIgualdad(ArrayList<TablaSimbolos> a)
        {
            
            for(TablaSimbolos  b : a)
            {
                if(p.getLexema().equals(b.getId()))
                {
                    ImprimirError(23);
                    throw new TerminacionMetodoException(""); 
                }
                    
            }
        }

        public void ErrorSintatico(){
            fr.getLabelSin().setForeground(new java.awt.Color(247, 36, 36));
            fr.getLabelSin().setText("----X----");;
            fr.getLabelSem().setForeground(new java.awt.Color(53, 97, 240));
            fr.getLabelSem().setText("----?----");
        }

}

class TerminacionMetodoException extends RuntimeException {
    public TerminacionMetodoException(String mensaje) {
        super(mensaje);
    }
}
