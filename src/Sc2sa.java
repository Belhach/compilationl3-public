import sa.*;
import sc.analysis.DepthFirstAdapter;
import sc.node.*;

public class Sc2sa extends DepthFirstAdapter {

    private SaNode returnValue;
    private int indentation = 0;

    @Override
    public void defaultIn(Node node) {
        for(int i = 0 ; i < indentation ; i++){
            System.out.print(" ");
        }
        System.out.println("<"+node.getClass().getSimpleName()+">");
        indentation++;
    }

    @Override
    public void defaultOut(Node node) {
        indentation--;
        for(int i = 0 ; i < indentation ; i++){
            System.out.print(" ");
        }
        System.out.println("</"+node.getClass().getSimpleName()+">");

    }

    @Override
    public void caseStart(Start node) {
        inStart(node);
        SaProg prog;
        node.getPProgramme().apply(this);
        prog = (SaProg) this.returnValue;
        this.returnValue = prog;
        outStart(node);
    }

    @Override
    public void caseADecvarldecfoncProgramme(ADecvarldecfoncProgramme node) {
        inADecvarldecfoncProgramme(node);
        SaLDec fonc;
        SaLDec var;
        node.getListedecfonc().apply(this);
        fonc = (SaLDec) this.returnValue;
        node.getOptdecvar().apply(this);
        var = (SaLDec) this.returnValue;
        this.returnValue = new SaProg(var,fonc);
        outADecvarldecfoncProgramme(node);
    }

    @Override
    public void caseALdecfoncProgramme(ALdecfoncProgramme node) {
        inALdecfoncProgramme(node);
        SaLDec fonc;
        node.getListedecfonc().apply(this);
        fonc = (SaLDec) this.returnValue;
        this.returnValue = new SaProg(null,fonc);
        outALdecfoncProgramme(node);
    }

    @Override
    public void caseAOptdecvar(AOptdecvar node) {
        inAOptdecvar(node);
        SaLDec var;
        node.getListedecvar().apply(this);
        var = (SaLDec) this.returnValue;
        returnValue = var;
        outAOptdecvar(node);
    }

    @Override
    public void caseADecvarldecvarListedecvar(ADecvarldecvarListedecvar node) {
        inADecvarldecvarListedecvar(node);
        SaDec var;
        SaLDec list_var;
        node.getDecvar().apply(this);
        var = (SaDec) this.returnValue;
        node.getListedecvarbis().apply(this);
        list_var = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(var,list_var);
        outADecvarldecvarListedecvar(node);
    }

    @Override
    public void caseADecvarListedecvar(ADecvarListedecvar node) {
        inADecvarListedecvar(node);
        SaDec var;
        node.getDecvar().apply(this);
        var = (SaDec) this.returnValue;
        this.returnValue = new SaLDec(var,null);
        outADecvarListedecvar(node);
    }

    @Override
    public void caseADecvarldecvarListedecvarbis(ADecvarldecvarListedecvarbis node) {
        inADecvarldecvarListedecvarbis(node);
        SaDec var;
        SaLDec list_var;
        node.getDecvar().apply(this);
        var = (SaDec) this.returnValue;
        node.getListedecvarbis().apply(this);
        list_var = (SaLDec) this.returnValue;
        this.returnValue = new SaLDec(var,list_var);
        outADecvarldecvarListedecvarbis(node);
    }

    @Override
    public void caseADecvarListedecvarbis(ADecvarListedecvarbis node) {
        inADecvarListedecvarbis(node);
        SaDec var;
        node.getDecvar().apply(this);
        var = (SaDec) this.returnValue;
        this.returnValue = new SaLDec(var,null);
        outADecvarListedecvarbis(node);
    }

    @Override
    public void caseADecvarentierDecvar(ADecvarentierDecvar node) {
        inADecvarentierDecvar(node);
        String nom;
        nom =  node.getIdentif().getText();
        returnValue = new SaDecVar(nom);
        outADecvarentierDecvar(node);
    }

    @Override
    public void caseADecvartableauDecvar(ADecvartableauDecvar node) {
        inADecvartableauDecvar(node);
        String nom;
        int taille;
        nom =  node.getIdentif().getText();
        taille =  Integer.parseInt(node.getNombre().getText());
        returnValue = new SaDecTab(nom,taille);
        outADecvartableauDecvar(node);
    }

    @Override
    public void caseALdecfoncrecListedecfonc(ALdecfoncrecListedecfonc node) {
        inALdecfoncrecListedecfonc(node);
        SaDecFonc fonc;
        SaLDec list_fonc;
        node.getDecfonc().apply(this);
        fonc = (SaDecFonc) this.returnValue;
        node.getListedecfonc().apply(this);
        list_fonc = (SaLDec) this.returnValue;
        returnValue = new SaLDec(fonc,list_fonc);
        outALdecfoncrecListedecfonc(node);
    }

    @Override
    public void caseALdecfoncfinalListedecfonc(ALdecfoncfinalListedecfonc node) {
        inALdecfoncfinalListedecfonc(node);
        returnValue = null;
        outALdecfoncfinalListedecfonc(node);
    }

    @Override
    public void caseADecvarinstrDecfonc(ADecvarinstrDecfonc node) {
        inADecvarinstrDecfonc(node);
        SaInst list_inst;
        SaLDec list_par;
        SaLDec list_var;
        String nom;
        nom = node.getIdentif().getText();
        node.getInstrbloc().apply(this);
        list_inst = (SaInst) this.returnValue;
        node.getListeparam().apply(this);
        list_par = (SaLDec) this.returnValue ;
        node.getOptdecvar().apply(this);
        list_var = (SaLDec) this.returnValue;
        this.returnValue = new SaDecFonc(nom,list_par,list_var,list_inst);
        outADecvarinstrDecfonc(node);
    }

    /* S O L O  C O D I N G */

    @Override
    public void caseAInstrDecfonc(AInstrDecfonc node) {
        inAInstrDecfonc(node);
        SaInst list_inst;
        SaLDec list_par;
        String nom;
        nom = node.getIdentif().getText();
        node.getInstrbloc().apply(this);
        list_inst = (SaInst) this.returnValue;
        node.getListeparam().apply(this);
        list_par = (SaLDec) this.returnValue ;
        this.returnValue = new SaDecFonc(nom,list_par,null,list_inst);
        outAInstrDecfonc(node);
    }

    @Override
    public void caseASansparamListeparam(ASansparamListeparam node) {
        inASansparamListeparam(node);
        returnValue = null;
        outASansparamListeparam(node);
    }

    @Override
    public void caseAAvecparamListeparam(AAvecparamListeparam node) {
        inAAvecparamListeparam(node);
        SaLDec list_vars;
        node.getListedecvar().apply(this);
        list_vars = (SaLDec) this.returnValue;
        returnValue = list_vars;
        outAAvecparamListeparam(node);
    }

    @Override
    public void caseAInstraffectInstr(AInstraffectInstr node) {
        inAInstraffectInstr(node);
        SaInstAffect affect_inst;
        node.getInstraffect().apply(this);
        affect_inst = (SaInstAffect) this.returnValue;
        this.returnValue = affect_inst;
        outAInstraffectInstr(node);
    }

    @Override
    public void caseAInstrblocInstr(AInstrblocInstr node) {
        inAInstrblocInstr(node);
        SaInstBloc bloc_inst;
        node.getInstrbloc().apply(this);
        bloc_inst = (SaInstBloc) this.returnValue;
        this.returnValue = bloc_inst;
        outAInstrblocInstr(node);
    }

    @Override
    public void caseAInstrsiInstr(AInstrsiInstr node) {
        inAInstrsiInstr(node);
        SaInstSi inst_si;
        node.getInstrsi().apply(this);
        inst_si = (SaInstSi) returnValue;
        returnValue = inst_si;
        outAInstrsiInstr(node);
    }

    @Override
    public void caseAInstrtantqueInstr(AInstrtantqueInstr node) {
        inAInstrtantqueInstr(node);
        SaInstTantQue inst_tant_que;
        node.getInstrtantque().apply(this);
        inst_tant_que = (SaInstTantQue) returnValue;
        returnValue = inst_tant_que;
        outAInstrtantqueInstr(node);
    }

    @Override
    public void caseAInstrappelInstr(AInstrappelInstr node) {
        inAInstrappelInstr(node);
        SaAppel inst_appl;
        node.getInstrappel().apply(this);
        inst_appl = (SaAppel) this.returnValue;
        returnValue = inst_appl;
        outAInstrappelInstr(node);
    }

    @Override
    public void caseAInstrretourInstr(AInstrretourInstr node) {
        inAInstrretourInstr(node);
        SaInstRetour inst_retour;
        node.getInstrretour().apply(this);
        inst_retour = (SaInstRetour) this.returnValue;
        returnValue = inst_retour;
        outAInstrretourInstr(node);
    }

    @Override
    public void caseAInstrecritureInstr(AInstrecritureInstr node) {
        inAInstrecritureInstr(node);
        SaInstEcriture instr_ecrire;
        node.getInstrecriture().apply(this);
        instr_ecrire = (SaInstEcriture) this.returnValue;
        returnValue = instr_ecrire;
        outAInstrecritureInstr(node);
    }

    @Override
    public void caseAInstrvideInstr(AInstrvideInstr node) {
        inAInstrvideInstr(node);
        SaInst inst_vide;
        node.getInstrvide().apply(this);
        inst_vide = (SaInst) this.returnValue;
        returnValue = inst_vide;
        outAInstrvideInstr(node);
    }

    @Override
    public void caseAInstraffect(AInstraffect node) {
        inAInstraffect(node);
        SaVar var;
        SaExp exp;
        node.getVar().apply(this);
        var = (SaVar) this.returnValue;
        node.getExp().apply(this);
        exp = (SaExp) this.returnValue;
        returnValue = new SaInstAffect(var,exp);
        outAInstraffect(node);
    }

    @Override
    public void caseAInstrbloc(AInstrbloc node) {
        inAInstrbloc(node);
        SaLInst list_inst;
        node.getListeinst().apply(this);
        list_inst = (SaLInst) this.returnValue;
        this.returnValue = new SaInstBloc(list_inst);
        outAInstrbloc(node);
    }

    @Override
    public void caseALinstrecListeinst(ALinstrecListeinst node) {
        inALinstrecListeinst(node);
        SaInst instr;
        SaLInst list_instr;
        node.getInstr().apply(this);
        instr = (SaInst) returnValue;
        node.getListeinst().apply(this);
        list_instr = (SaLInst) returnValue;
        returnValue = new SaLInst(instr,list_instr);
        outALinstrecListeinst(node);
    }

    @Override
    public void caseALinstfinalListeinst(ALinstfinalListeinst node) {

        inALinstfinalListeinst(node);
        returnValue = null;
        outALinstfinalListeinst(node);
    }

    @Override
    public void caseAAvecsinonInstrsi(AAvecsinonInstrsi node) {
        inAAvecsinonInstrsi(node);
        SaExp exp;
        SaInst alors;
        SaInst sinon;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        node.getInstrbloc().apply(this);
        alors = (SaInst) returnValue;
        node.getInstrsinon().apply(this);
        sinon = (SaInst) returnValue;
        returnValue = new SaInstSi(exp,alors,sinon);
        outAAvecsinonInstrsi(node);
    }

    @Override
    public void caseASanssinonInstrsi(ASanssinonInstrsi node) {
        inASanssinonInstrsi(node);
        SaExp exp;
        SaInst alors;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        node.getInstrbloc().apply(this);
        alors = (SaInst) returnValue;
        returnValue = new SaInstSi(exp,alors,null);
        outASanssinonInstrsi(node);
    }

    @Override
    public void caseAInstrsinon(AInstrsinon node) {
        inAInstrsinon(node);
        SaInstBloc inst;
        node.getInstrbloc().apply(this);
        inst = (SaInstBloc) returnValue;
        returnValue = inst;
        outAInstrsinon(node);
    }

    @Override
    public void caseAInstrtantque(AInstrtantque node) {
        inAInstrtantque(node);
        SaExp exp;
        SaInst faire;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        node.getInstrbloc().apply(this);
        faire = (SaInst) returnValue;
        returnValue = new SaInstTantQue(exp,faire);
        outAInstrtantque(node);
    }

    @Override
    public void caseAInstrappel(AInstrappel node) {
        inAInstrappel(node);
        SaAppel appel;
        node.getAppelfct().apply(this);
        appel = (SaAppel) returnValue;
        returnValue = appel;
        outAInstrappel(node);
    }

    @Override
    public void caseAInstrretour(AInstrretour node) {
        inAInstrretour(node);
        SaExp exp;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        returnValue = new SaInstRetour(exp);
        outAInstrretour(node);
    }

    @Override
    public void caseAInstrecriture(AInstrecriture node) {
        inAInstrecriture(node);
        SaExp exp;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        returnValue = new SaInstEcriture(exp);
        outAInstrecriture(node);
    }

    @Override
    public void caseAInstrvide(AInstrvide node) {
        inAInstrvide(node);
        returnValue = null;
        outAInstrvide(node);
    }

    @Override
    public void caseAOuExp(AOuExp node) {
        inAOuExp(node);
        SaExp expr1;
        SaExp expr2;
        node.getExp().apply(this);
        expr1 = (SaExp) returnValue ;
        node.getExp().apply(this);
        expr2 = (SaExp) returnValue;
        returnValue = new SaExpOr(expr1,expr2);
        outAOuExp(node);
    }

    @Override
    public void caseAExp1Exp(AExp1Exp node) {
        inAExp1Exp(node);
        SaExp exp;
        node.getExp1().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAExp1Exp(node);
    }

    @Override
    public void caseAEtExp1(AEtExp1 node) {
        inAEtExp1(node);
        SaExp expr1;
        SaExp expr2;
        node.getExp1().apply(this);
        expr1 = (SaExp) returnValue ;
        node.getExp2().apply(this);
        expr2 = (SaExp) returnValue;
        returnValue = new SaExpAnd(expr1,expr2);
        outAEtExp1(node);
    }

    @Override
    public void caseAExp2Exp1(AExp2Exp1 node) {
        inAExp2Exp1(node);
        SaExp exp;
        node.getExp2().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAExp2Exp1(node);
    }

    @Override
    public void caseAInfExp2(AInfExp2 node) {
        inAInfExp2(node);
        SaExp expr2;
        SaExp expr3;
        node.getExp2().apply(this);
        expr2 = (SaExp) returnValue ;
        node.getExp3().apply(this);
        expr3 = (SaExp) returnValue;
        returnValue = new SaExpInf(expr2,expr3);
        outAInfExp2(node);
    }

    @Override
    public void caseAEgalExp2(AEgalExp2 node) {
        inAEgalExp2(node);
        SaExp expr2;
        SaExp expr3;
        node.getExp2().apply(this);
        expr2 = (SaExp) returnValue ;
        node.getExp3().apply(this);
        expr3 = (SaExp) returnValue;
        returnValue = new SaExpEqual(expr2,expr3);
        outAEgalExp2(node);
    }

    @Override
    public void caseAExp3Exp2(AExp3Exp2 node) {
        inAExp3Exp2(node);
        SaExp exp;
        node.getExp3().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAExp3Exp2(node);
    }

    @Override
    public void caseAPlusExp3(APlusExp3 node) {
        inAPlusExp3(node);
        SaExp expr3;
        SaExp expr4;
        node.getExp3().apply(this);
        expr3 = (SaExp) returnValue ;
        node.getExp4().apply(this);
        expr4 = (SaExp) returnValue;
        returnValue = new SaExpAdd(expr3,expr4);
        outAPlusExp3(node);
    }

    @Override
    public void caseAMoinsExp3(AMoinsExp3 node) {
        inAMoinsExp3(node);
        SaExp expr3;
        SaExp expr4;
        node.getExp3().apply(this);
        expr3 = (SaExp) returnValue ;
        node.getExp4().apply(this);
        expr4 = (SaExp) returnValue;
        returnValue = new SaExpSub(expr3,expr4);
        outAMoinsExp3(node);
    }

    @Override
    public void caseAExp4Exp3(AExp4Exp3 node) {
        inAExp4Exp3(node);
        SaExp exp;
        node.getExp4().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAExp4Exp3(node);

    }

    @Override
    public void caseAFoisExp4(AFoisExp4 node) {
        inAFoisExp4(node);
        SaExp expr4;
        SaExp expr5;
        node.getExp4().apply(this);
        expr4 = (SaExp) returnValue ;
        node.getExp5().apply(this);
        expr5 = (SaExp) returnValue;
        returnValue = new SaExpMult(expr4,expr5);
        outAFoisExp4(node);
    }

    @Override
    public void caseADiviseExp4(ADiviseExp4 node) {
        inADiviseExp4(node);
        SaExp expr4;
        SaExp expr5;
        node.getExp4().apply(this);
        expr4 = (SaExp) returnValue ;
        node.getExp5().apply(this);
        expr5 = (SaExp) returnValue;
        returnValue = new SaExpDiv(expr4,expr5);
        outADiviseExp4(node);
    }

    @Override
    public void caseAExp5Exp4(AExp5Exp4 node) {
        inAExp5Exp4(node);
        SaExp exp;
        node.getExp5().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAExp5Exp4(node);
    }

    @Override
    public void caseANonExp5(ANonExp5 node) {
        super.caseANonExp5(node);
    }

    @Override
    public void caseAExp6Exp5(AExp6Exp5 node) {
        inAExp6Exp5(node);
        SaExp exp;
        node.getExp6().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAExp6Exp5(node);
    }

    @Override
    public void caseANombreExp6(ANombreExp6 node) {
        inANombreExp6(node);
        int nombre;
        nombre = Integer.parseInt(node.getNombre().getText());
        returnValue = new SaExpInt(nombre);
        outANombreExp6(node);
    }

    @Override
    public void caseAAppelfctExp6(AAppelfctExp6 node) {
        inAAppelfctExp6(node);
        SaAppel appel;
        node.getAppelfct().apply(this);
        appel = (SaAppel) returnValue;
        returnValue = new SaExpAppel(appel);
        outAAppelfctExp6(node);
    }

    @Override
    public void caseAVarExp6(AVarExp6 node) {
        inAVarExp6(node);
        SaVar var;
        node.getVar().apply(this);
        var = (SaVar) this.returnValue;
        returnValue = new SaExpVar(var);
        outAVarExp6(node);
    }

    @Override
    public void caseAParenthesesExp6(AParenthesesExp6 node) {
        inAParenthesesExp6(node);
        SaExp exp;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        returnValue = exp;
        outAParenthesesExp6(node);
    }

    @Override
    public void caseALireExp6(ALireExp6 node) {
        inALireExp6(node);
        returnValue = new SaExpLire();
        outALireExp6(node);
    }

    @Override
    public void caseAVartabVar(AVartabVar node) {
        inAVartabVar(node);
        String nom;
        SaExp index;
        nom = node.getIdentif().getText();
        node.getExp().apply(this);
        index = (SaExp) returnValue;
        returnValue = new SaVarIndicee(nom,index);
        outAVartabVar(node);
    }

    @Override
    public void caseAVarsimpleVar(AVarsimpleVar node) {
        inAVarsimpleVar(node);
        String nom;
        nom = node.getIdentif().getText();
        returnValue = new SaVarSimple(nom);
        outAVarsimpleVar(node);
    }

    @Override
    public void caseARecursifListeexp(ARecursifListeexp node) {
        inARecursifListeexp(node);
        SaExp exp;
        SaLExp list_exp;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        node.getListeexpbis().apply(this);
        list_exp = (SaLExp) returnValue;
        returnValue = new SaLExp(exp,list_exp);
        outARecursifListeexp(node);
    }

    @Override
    public void caseAFinalListeexp(AFinalListeexp node) {
        inAFinalListeexp(node);
        returnValue = null;
        outAFinalListeexp(node);
    }

    @Override
    public void caseARecursifListeexpbis(ARecursifListeexpbis node) {
        inARecursifListeexpbis(node);
        SaExp exp;
        SaLExp list_exp;
        node.getExp().apply(this);
        exp = (SaExp) returnValue;
        node.getListeexpbis().apply(this);
        list_exp = (SaLExp) returnValue;
        returnValue = new SaLExp(exp,list_exp);
        outARecursifListeexpbis(node);
    }

    @Override
    public void caseAFinalListeexpbis(AFinalListeexpbis node) {
        inAFinalListeexpbis(node);
        returnValue = null;
        outAFinalListeexpbis(node);
    }

    @Override
    public void caseAAppelfct(AAppelfct node) {
        inAAppelfct(node);
        String nom;
        SaLExp list_exp;
        nom = node.getIdentif().getText();
        node.getListeexp().apply(this);
        list_exp = (SaLExp) returnValue;
        returnValue = new SaAppel(nom,list_exp);
        outAAppelfct(node);
    }

    public SaNode getRoot(){
        return this.returnValue;
    }
}
