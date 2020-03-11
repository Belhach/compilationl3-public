import sa.*;
import ts.Ts;
import ts.TsItemFct;
import ts.TsItemVar;

public class Sa2ts extends SaDepthFirstVisitor{
    private Ts Global_Table ;
    private Ts Local_Tables;
    public static final String GLOBAL = "GLOBAL";
    public static final String PARAMETRE = "PARAMETRE";
    public static final String VARIABLE = "VARIABLE";
    public String State;

    private int indentation = 0;

    public Sa2ts(SaNode root){
        Global_Table = new Ts();
        State = GLOBAL;
        root.accept(this);
    }

    public void defaultIn(SaNode node)
    {
        for(int i = 0 ; i < indentation ; i++){
            System.out.print(" ");
        }
        System.out.println("<"+node.getClass().getSimpleName()+">");
        indentation++;
    }

    public void defaultOut(SaNode node)
    {
        indentation--;
        for(int i = 0 ; i < indentation ; i++){
            System.out.print(" ");
        }
        System.out.println("</"+node.getClass().getSimpleName()+">");
    }

    public Void visit(SaDecVar node)
    {
        defaultIn(node);
        if(State == GLOBAL) {
            if (varExists(node.getNom(), Global_Table))
                throw new RuntimeException("cette variable a deja été déclaré");
            node.tsItem = Global_Table.addVar(node.getNom(), 1);
        }
        if(State == PARAMETRE) {
            if (varExists(node.getNom(), Local_Tables))
                throw new RuntimeException("cette variable a deja été déclaré");
            node.tsItem = Local_Tables.addParam(node.getNom());
        }
        if(State == VARIABLE) {
            if (varExists(node.getNom(), Global_Table))
                throw new RuntimeException("cette variable a deja été déclaré");
            node.tsItem = Local_Tables.addVar(node.getNom(), 1);
        }
        defaultOut(node);
        return null;
    }

    public Void visit(SaDecTab node){
        defaultIn(node);
        if (State != GLOBAL) throw new RuntimeException("le tableau doit etre declaré dans la table global");
        String nom = node.getNom();
        int taille = node.getTaille();
        if(varExists(nom, Global_Table)) throw new RuntimeException("Cette variable existe deja");
        if (taille < 2) throw new RuntimeException("la taille du tableau doit etre superieur à 1");
        else node.tsItem = Global_Table.addVar(nom,taille);
        defaultOut(node);
        return null;
    }

    public Void visit(SaDecFonc node){
        defaultIn(node);
        Ts new_local_table = new Ts();
        Local_Tables = new_local_table;
        int nbr_args;
        if(node.getParametres() == null) {
            nbr_args = 0;
        }
        else {
            nbr_args = node.getParametres().length();
        }
        State = GLOBAL;
        if (FncExists(node.getNom())) throw new RuntimeException("la fonction a deja ete declaré");
        State = PARAMETRE;
        if (node.getParametres() != null) node.getParametres().accept(this);
        State = VARIABLE;
        if (node.getVariable() != null) node.getVariable().accept(this);
        if (node.getCorps() != null) node.getCorps().accept(this);
        State = GLOBAL;
        Local_Tables = Global_Table.getTableLocale(node.getNom());
        node.tsItem = Global_Table.addFct(node.getNom(),nbr_args,new_local_table,node);
        defaultOut(node);
        return null;
    }

    public Void visit(SaVarSimple node)
    {
        defaultIn(node);
        if(!varExists(node.getNom(), Local_Tables) && !varExists(node.getNom(), Global_Table)){
            throw new RuntimeException("cette variable n'as pas été déclaré");
        }
        TsItemVar tsItemVar;
        if (State == GLOBAL) {
            tsItemVar = Global_Table.getVar(node.getNom());
        } else {
            tsItemVar = Local_Tables.getVar(node.getNom());
        }
        node.tsItem = tsItemVar;
        defaultOut(node);
        return null;
    }


    public Void visit(SaVarIndicee node)
    {
        defaultIn(node);
        String nom = node.getNom();
        if(varExists(nom, Local_Tables)) throw new RuntimeException(nom + "est une variable local");
        if(!varExists(nom, Global_Table)) throw new RuntimeException(nom + "n'as pas été déclaré");
        node.tsItem = Global_Table.getVar(nom);
        defaultOut(node);
        return null;
    }

    public Void visit(SaAppel node)
    {
        defaultIn(node);
        String nom = node.getNom();
        int nbr_args;
        if(node.getArguments() == null) nbr_args = 0;
        else nbr_args = node.getArguments().length();
        TsItemFct tsItemFct = Global_Table.getFct(nom);
        if(!FncExists(nom)) throw new RuntimeException("cette fonction n'existe pas");
        if(nbr_args != tsItemFct.nbArgs) throw new RuntimeException("le nombre d'argument n'est pas celui attendu");
        node.tsItem = tsItemFct;
        defaultOut(node);
        return null;
    }

    private boolean varExists(String nom, Ts table) {
        return table.getVar(nom) != null;
    }

    private boolean FncExists(String nom) {
        return Global_Table.getFct(nom) != null;
    }

    public Ts getTableGlobale() {
        return Global_Table;
    }
}
