public class TablaSimbolos {
    private String TipoDato;
    private String Id;
    private String Renglon;
    private TablaSimbolos Nexo;
        
        
    public TablaSimbolos(String tipoDato, String id, String renglon)
    {
        this.TipoDato = tipoDato;
        this.Id= id;
        this.Renglon = renglon;
    }
    
    public TablaSimbolos()
    {
       
    }
    
     //Encapsulamiento
    protected String  getDato()
    {
        return TipoDato;
    }
    
    protected void setDato(String a)
    {
        this.TipoDato  = a;
    }
    
    protected String getId()
    {
        return Id;
    }
   
    protected void setId (String a)
    {
        this.Id = a;
    }
    
    protected String getRenglon()
    {
        return Renglon;
    }
   
    protected void setRenglon (String a)
    {
        this.Renglon = a;
    }
    
    protected TablaSimbolos getNexo()
    {
        return Nexo;
    }
   
    protected void setUnion (TablaSimbolos a)
    {
        this.Nexo = a;
    }
}
