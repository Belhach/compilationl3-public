import c3a.*;
import sa.*;
import ts.Ts;
import ts.TsItemVar;

public class Sa2c3a extends SaDepthFirstVisitor<C3aOperand> {
    private C3a c3a;
    private Ts TsTable;
    private int indentation = 0;

    public Sa2c3a(SaNode root,Ts TsTable){
        c3a = new C3a();
        this.TsTable = TsTable;
        root.accept(this);
    }

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
    public C3aOperand visit(SaExpInt node)
    {
        defaultIn(node);
        C3aConstant var = new C3aConstant(node.getVal());
        defaultOut(node);
        return var;
    }

    @Override
    public C3aOperand visit(SaExpVar node)
    {
        defaultIn(node);
        SaVar var = node.getVar();
        C3aOperand operand = null;
        if(var != null){
            operand = var.accept(this);
        }
        defaultOut(node);
        return operand;
    }

    @Override
    public C3aOperand visit(SaInstTantQue node)
    {
        defaultIn(node);
        C3aLabel new_label = c3a.newAutoLabel();
        c3a.addLabelToNextInst(new_label);
        //c3a.ajouteInst(new C3aInstJumpIfEqual(,,""));
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
    public C3aOperand visit(SaInstAffect node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }


    @Override
    public C3aOperand visit(SaVarSimple node)
    {
        defaultIn(node);
        TsItemVar var = node.tsItem;
        defaultOut(node);
        return new C3aVar(var,null);
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
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null){
            operand2 = op2.accept(this);
        }
        C3aTemp var_temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstAdd(operand1,operand2,var_temp,""));
        defaultOut(node);
        return var_temp;
    }

    @Override
    public C3aOperand visit(SaExpSub node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null){
            operand2 = op2.accept(this);
        }
        C3aTemp var_temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstSub(operand1,operand2,var_temp,""));
        defaultOut(node);
        return var_temp;
    }

    @Override
    public C3aOperand visit(SaExpMult node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null){
            operand2 = op2.accept(this);
        }
        C3aTemp var_temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstMult(operand1,operand2,var_temp,""));
        defaultOut(node);
        return var_temp;
    }

    @Override
    public C3aOperand visit(SaExpDiv node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null){
            operand2 = op2.accept(this);
        }
        C3aTemp var_temp = c3a.newTemp();

        c3a.ajouteInst(new C3aInstDiv(operand1,operand2,var_temp,""));
        defaultOut(node);
        return var_temp;
    }

    @Override
    public C3aOperand visit(SaExpInf node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        C3aTemp var_temp = c3a.newTemp();
        C3aLabel label = c3a.newAutoLabel();
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null){
            operand2 = op2.accept(this);
        }
        c3a.ajouteInst(new C3aInstAffect(c3a.True,var_temp,""));
        c3a.ajouteInst(new C3aInstJumpIfLess(operand1,operand2,label,""));
        c3a.ajouteInst(new C3aInstAffect(c3a.False,var_temp,""));
        c3a.addLabelToNextInst(label);
        defaultOut(node);
        return var_temp;
    }

    @Override
    public C3aOperand visit(SaExpEqual node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        C3aTemp var_temp = c3a.newTemp();
        C3aLabel label = c3a.newAutoLabel();
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null){
            operand2 = op2.accept(this);
        }
        c3a.ajouteInst(new C3aInstAffect(c3a.True,var_temp,""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(operand1,operand2,label,""));
        c3a.ajouteInst(new C3aInstAffect(c3a.False,var_temp,""));
        c3a.addLabelToNextInst(label);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpAnd node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null) {
            operand2 = op2.accept(this);
        }
        C3aLabel False_label = c3a.newAutoLabel();
        C3aTemp var_temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstJumpIfEqual(operand1,c3a.False,False_label,""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(operand2,c3a.False,False_label,""));

        c3a.ajouteInst(new C3aInstAffect(var_temp,c3a.True,""));
        C3aLabel True_label = c3a.newAutoLabel();
        c3a.ajouteInst(new C3aInstJump(True_label,""));

        c3a.addLabelToNextInst(False_label);
        c3a.ajouteInst(new C3aInstAffect(c3a.False,var_temp,""));
        c3a.addLabelToNextInst(True_label);
        defaultOut(node);
        return var_temp;
    }


    @Override
    public C3aOperand visit(SaExpOr node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand1 = null;
        C3aOperand operand2 = null;
        if(op1 != null){
            operand1 = op1.accept(this);
        }
        if(op2 != null) {
            operand2 = op2.accept(this);
        }
        C3aLabel False_label = c3a.newAutoLabel();
        C3aTemp var_temp = c3a.newTemp();
        c3a.ajouteInst(new C3aInstJumpIfNotEqual(operand1,c3a.False,False_label,""));
        c3a.ajouteInst(new C3aInstJumpIfNotEqual(operand2,c3a.False,False_label,""));

        c3a.ajouteInst(new C3aInstAffect(var_temp,c3a.True,""));
        C3aLabel True_label = c3a.newAutoLabel();
        c3a.ajouteInst(new C3aInstJump(True_label,""));

        c3a.addLabelToNextInst(False_label);
        c3a.ajouteInst(new C3aInstAffect(c3a.False,var_temp,""));
        c3a.addLabelToNextInst(True_label);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaExpNot node)
    {
        defaultIn(node);
        SaExp op1 = node.getOp1();
        SaExp op2 = node.getOp2();
        C3aOperand operand = null;
        C3aTemp var_temp = c3a.newTemp();
        C3aLabel Label = c3a.newAutoLabel();
        if(op1 != null){
            operand = op1.accept(this);
        }
        c3a.ajouteInst(new C3aInstAffect(c3a.True,var_temp,""));
        c3a.ajouteInst(new C3aInstJumpIfEqual(operand,c3a.False,Label,""));
        c3a.ajouteInst(new C3aInstAffect(c3a.False,var_temp,""));
        c3a.addLabelToNextInst(Label);
        defaultOut(node);
        return var_temp;
    }

    @Override
    public C3aOperand visit(SaExpLire node)
    {
        defaultIn(node);
        defaultOut(node);
        return super.visit(node);
    }


    @Override
    public C3aOperand visit(SaInstSi node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    public C3aOperand visit(SaInstRetour node)
    {
        defaultIn(node);
        defaultOut(node);
        return null;
    }

    @Override
    public C3aOperand visit(SaVarIndicee node)
    {
        defaultIn(node);
        TsItemVar var = node.tsItem;
        C3aOperand length = null;
        if(node.getIndice() != null){
            length = node.getIndice().accept(this);
        }
        defaultOut(node);
        return new C3aVar(var,length);
    }

    public C3a getC3a(){
        return c3a;
    }
}
