import sa.*;
import ts.Ts;
import ts.TsItemFct;
import ts.TsItemVar;

public class Sa2ts extends SaDepthFirstVisitor{
    private Ts Global_Table ;
    private Ts Local_Tables;
    private boolean isParam;

    public Sa2ts(SaNode root){
        Global_Table = new Ts();
        root.accept(this);
    }

    public Ts getTableGlobale() {
        return Global_Table;
    }

    public void defaultIn(SaNode node)
    {
    }

    public void defaultOut(SaNode node)
    {
    }

    public Void visit(SaProg node)
    {
        defaultIn(node);
        if(node.getVariables() != null)
            node.getVariables().accept(this);
        if(node.getFonctions() != null)
            node.getFonctions().accept(this);
        defaultOut(node);
        return null;
    }

    // DEC -> var id taille
    public Void visit(SaDecTab node){
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    public Void visit(SaExp node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    // EXP -> entier
    public Void visit(SaExpInt node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }
    public Void visit(SaExpVar node)
    {
        defaultIn(node);
        node.getVar().accept(this);
        defaultOut(node);
        return null;
    }

    public Void visit(SaInstEcriture node)
    {
        defaultIn(node);
        node.getArg().accept(this);
        defaultOut(node);
        return null;
    }

    public Void visit(SaInstTantQue node)
    {
        defaultIn(node);
        node.getTest().accept(this);
        node.getFaire().accept(this);
        defaultOut(node);
        return null;
    }
    public Void visit(SaLInst node)
    {
        defaultIn(node);
        node.getTete().accept(this);
        if(node.getQueue() != null) node.getQueue().accept(this);
        defaultOut(node);
        return null;
    }

    // DEC -> fct id LDEC LDEC LINST
    public Void visit(SaDecFonc node)
    {
        defaultIn(node);
        if(node.getParametres() != null) node.getParametres().accept(this);
        if(node.getVariable() != null) node.getVariable().accept(this);
        node.getCorps().accept(this);
        defaultOut(node);
        return null;
    }

    // DEC -> var id
    public Void visit(SaDecVar node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    public Void visit(SaInstAffect node)
    {
        defaultIn(node);
        node.getLhs().accept(this);
        node.getRhs().accept(this);
        defaultOut(node);
        return null;
    }

    // LDEC -> DEC LDEC
    // LDEC -> null
    public Void visit(SaLDec node)
    {
        defaultIn(node);
        node.getTete().accept(this);
        if(node.getQueue() != null) node.getQueue().accept(this);
        defaultOut(node);
        return null;
    }

    public Void visit(SaVarSimple node)
    {
        defaultIn(node);
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

    public Void visit(SaExpAppel node)
    {
        defaultIn(node);
        node.getVal().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> add EXP EXP
    public Void visit(SaExpAdd node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> sub EXP EXP
    public Void visit(SaExpSub node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> mult EXP EXP
    public Void visit(SaExpMult node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> div EXP EXP
    public Void visit(SaExpDiv node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> inf EXP EXP
    public Void visit(SaExpInf node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> eq EXP EXP
    public Void visit(SaExpEqual node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> and EXP EXP
    public Void visit(SaExpAnd node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }


    // EXP -> or EXP EXP
    public Void visit(SaExpOr node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        node.getOp2().accept(this);
        defaultOut(node);
        return null;
    }

    // EXP -> not EXP
    public Void visit(SaExpNot node)
    {
        defaultIn(node);
        node.getOp1().accept(this);
        defaultOut(node);
        return null;
    }


    public Void visit(SaExpLire node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    public Void visit(SaInstBloc node)
    {
        defaultIn(node);
        node.getVal().accept(this);
        defaultOut(node);
        return null;
    }

    public Void visit(SaInstSi node)
    {
        defaultIn(node);
        node.getTest().accept(this);
        node.getAlors().accept(this);
        if(node.getSinon() != null) node.getSinon().accept(this);
        defaultOut(node);
        return null;
    }

    // INST -> ret EXP
    public Void visit(SaInstRetour node)
    {
        defaultIn(node);
        node.getVal().accept(this);
        defaultOut(node);
        return null;
    }


    public Void visit(SaLExp node)
    {
        defaultIn(node);
        node.getTete().accept(this);
        if(node.getQueue() != null)
            node.getQueue().accept(this);
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
}
