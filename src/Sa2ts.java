import sa.*;
import ts.Ts;
import ts.TsItemFct;
import ts.TsItemVar;

public class Sa2ts extends SaDepthFirstVisitor{
    private Ts Global_Table ;
    private Ts Local_Tables;
    private boolean isGlobal;
    private boolean isParam;
    private boolean isVariable;
    private int indentation = 0;

    public Sa2ts(SaNode root){
        Global_Table = new Ts();
        root.accept(this);
        isGlobal = true;
        isParam = false;
        isVariable = false;
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
        if(isGlobal) {
            if (Global_Table.variables.containsKey(node.getNom()))
                throw new RuntimeException("cette variable a deja été déclaré");
            node.tsItem = Global_Table.addVar(node.getNom(), 1);
        }
        isParam = true;
        if(isParam) {
            if (Local_Tables.variables.containsKey(node.getNom()))
                throw new RuntimeException("cette variable a deja été déclaré");
            node.tsItem = Local_Tables.addVar(node.getNom(), 1);
        }
        isVariable = true;
        if(isVariable) {
            if (Local_Tables.variables.containsKey(node.getNom()))
                throw new RuntimeException("cette variable a deja été déclaré");
            node.tsItem = Local_Tables.addVar(node.getNom(), 1);
        }
        isParam = false;
        isVariable = false;
        defaultOut(node);
        return null;
    }

    public Void visit(SaDecTab node){
        defaultIn(node);
        String nom = node.getNom();
        int taille = node.getTaille();
        if(Global_Table.variables.containsKey(nom)){ throw new RuntimeException("Cette variable existe deja");}
        else node.tsItem = Global_Table.addVar(nom,taille);
        defaultOut(node);
        return null;
    }

    public Void visit(SaDecFonc node){
        defaultIn(node);
        Local_Tables = new Ts();
        String nom = node.getNom();
        int nbr_args;
        if(node.getParametres() != null) nbr_args = 0;
        else nbr_args = node.getParametres().length();
        if (node.getParametres() != null) node.getParametres().accept(this);
        if (node.getVariable() != null) node.getVariable().accept(this);
        if (node.getCorps() != null) node.getCorps().accept(this);
        Local_Tables = Global_Table.getTableLocale(node.getNom());
        node.tsItem = Global_Table.addFct(nom,nbr_args,Local_Tables,node);
        defaultOut(node);
        return null;
    }

    public Void visit(SaVarSimple node)
    {
        defaultIn(node);

        defaultOut(node);
        return null;
    }

    public Void visit(SaVarIndicee node)
    {
        defaultIn(node);
        node.getIndice().accept(this);
        defaultOut(node);
        return null;
    }

    public Void visit(SaAppel node)
    {
        defaultIn(node);
        if(node.getArguments() != null) node.getArguments().accept(this);
        defaultOut(node);
        return null;
    }


    public Ts getTableGlobale() {
        return Global_Table;
    }
}
