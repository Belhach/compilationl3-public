import c3a.C3a;
import c3a.C3aOperand;
import sa.*;
import ts.Ts;

public class Sa2c3a extends SaDepthFirstVisitor<C3aOperand> {
    private C3a c3a;
    private Ts TsTable;
    private int indentation = 0;

    @Override
    public void defaultIn(SaNode node)
    {
        for(int i = 0 ; i < indentation ; i++){
            System.out.print(" ");
        }
        System.out.println("<"+node.getClass().getSimpleName()+">");
        indentation++;
    }

    @Override
    public void defaultOut(SaNode node)
    {
        indentation--;
        for(int i = 0 ; i < indentation ; i++){
            System.out.print(" ");
        }
        System.out.println("</"+node.getClass().getSimpleName()+">");
    }

    @Override
    public C3aOperand visit(SaProg node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaDecTab node){
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExp node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpInt node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpVar node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstEcriture node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaLInst node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaDecFonc node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaDecVar node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstAffect node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaLDec node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaVarSimple node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaAppel node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAppel node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAdd node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpSub node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpMult node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpDiv node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpInf node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpEqual node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAnd node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }


    @Override
    public C3aOperand visit(SaExpOr node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpNot node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpLire node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstBloc node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstSi node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaInstRetour node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaLExp node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaVarIndicee node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }
}
