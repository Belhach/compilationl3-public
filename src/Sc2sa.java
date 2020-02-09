import java.io.*;
import sc.analysis.*;
import sc.node.*;
import sa.*;

class Sc2sa extends DepthFirstAdapter
{
	private SaNode returnValue;

	@Override
	public void caseStart(Start node) {
		inStart(node);
		SaProg prog ;
		node.getPProgramme().apply(this);
		prog = (SaProg) this.returnValue;
		this.returnValue = prog;
	}

	@Override
	public void caseAProgramme(AProgramme node){
		inAProgramme(node);
		SaLDec vars;
		SaLDec fncs;
		node.getOptdecvar().apply(this);
		vars = (SaLDec) this.returnValue;
		node.getListedecfonc().apply(this);
		fncs = (SaLDec) this.returnValue;
		this.returnValue = new SaProg(vars,fncs);
	}

	@Override
	public void caseADeclaGlobOptdecvar(ADeclaGlobOptdecvar node){
		inADeclaGlobOptdecvar(node);
		SaLDec var ;
		node.getDeclaVariable().apply(this);
		var = (SaLDec) this.returnValue;
		this.returnValue = var;
	}

	@Override
	public void caseAVideOptdecvar(AVideOptdecvar node){

		inAVideOptdecvar(node);
		this.returnValue = null;

	}

	@Override
	public void caseAMainFncListedecfonc(AMainFncListedecfonc node){
		inAMainFncListedecfonc(node);
		SaDecFonc fnc ;
		node.getMainFonc().apply(this);
		fnc = (SaDecFonc) this.returnValue;
		this.returnValue = new SaLDec(fnc,null);
	}

	@Override
	public void caseAAutreFncListedecfonc(AAutreFncListedecfonc node){
		inAAutreFncListedecfonc(node);
		SaDecFonc fnc;
		SaLDec fncs;
		node.getFonction().apply(this);
		fnc = (SaDecFonc) this.returnValue;
		node.getListedecfonc().apply(this);
		fncs = (SaLDec) this.returnValue;
		this.returnValue = new SaLDec(fnc,fncs);
	}

	@Override
	public void caseAMainMainFonc(AMainMainFonc node){
		/*inAMainMainFonc(node);
		node.getMain().apply(this);
		node.getParentheseOuvrante().apply(this);
		node.getParentheseFermante().apply(this);
		node.getOptdecvar().apply(this);
		node.getBlocInst().apply(this);*/
	}

	@Override
	public void caseAExprListExpr(AExprListExpr node){
		inAExprListExpr(node);
		SaExp exp;
		SaLExp L_exp;
		node.getExpr().apply(this);
		exp = (SaExp) this.returnValue;
		node.getSousListExpr().apply(this);
		L_exp = (SaLExp) this.returnValue;
		this.returnValue = new SaLExp(exp,L_exp);
	}

	@Override
	public void caseAVideListExpr(AVideListExpr node){
		inAVideListExpr(node);
		this.returnValue = null;
	}

	@Override
	public void caseAListExprSousListExpr(AListExprSousListExpr node){
		inAListExprSousListExpr(node);

		//node.getVirgule().apply(this);
		SaExp exp;
		SaLExp exps;
		node.getExpr().apply(this);
		exp = (SaExp) this.returnValue;
		node.getSousListExpr().apply(this);
		exps = (SaLExp) this.returnValue;
		returnValue = new SaLExp(exp,exps);
	}

	@Override
	public void caseAVideSousListExpr(AVideSousListExpr node){
		inAVideSousListExpr(node);
		this.returnValue = null;
	}

	@Override
	public void caseAExp1Expr(AExp1Expr node){
		inAExp1Expr(node);
		SaExp exp1;
		node.getExp1().apply(this);
		exp1 = (SaExp) returnValue;
		returnValue = exp1;
	}

	public void caseAOuExpr(AOuExpr node){
		inAOuExpr(node);
		SaExp exp1 ;
		SaExp expr ;
		node.getExp1().apply(this);
		exp1 = (SaExp) returnValue;
		node.getExpr().apply(this);
		expr = (SaExp) returnValue;
		returnValue = new SaExpOr(exp1,expr);
	}

	@Override
	public void caseAExp2Exp1(AExp2Exp1 node){
		inAExp2Exp1(node);
		SaExp exp2 ;
		node.getExp2().apply(this);
		exp2 = (SaExp) returnValue;
		returnValue = exp2;
	}

	@Override
	public void caseAEtExp1(AEtExp1 node){
		inAEtExp1(node);
		SaExp exp1 ;
		SaExp exp2 ;
		node.getExp1().apply(this);
		exp1 = (SaExp) returnValue;
		node.getExp2().apply(this);
		exp2 = (SaExp) returnValue;
		returnValue = new SaExpAnd(exp1,exp2);
	}

	@Override
	public void caseAExp3Exp2(AExp3Exp2 node){
		inAExp3Exp2(node);
		SaExp exp3 ;
		node.getExp3().apply(this);
		exp3 = (SaExp) returnValue;
		returnValue = exp3;
	}

	@Override
	public void caseAEgaleExp2(AEgaleExp2 node){
		inAEgaleExp2(node);
		SaExp exp2 ;
		SaExp exp3 ;
		node.getExp2().apply(this);
		exp2 = (SaExp) returnValue;
		node.getExp3().apply(this);
		exp3 = (SaExp) returnValue;
		returnValue = new SaExpEqual(exp2,exp3);
	}

	@Override
	public void caseAInferieurExp2(AInferieurExp2 node){
		inAInferieurExp2(node);
		SaExp exp2 ;
		SaExp exp3 ;
		node.getExp2().apply(this);
		exp2 = (SaExp) returnValue;
		node.getExp3().apply(this);
		exp3 = (SaExp) returnValue;
		returnValue = new SaExpInf(exp2,exp3);
	}

	@Override
	public void caseAExp4Exp3(AExp4Exp3 node){
		SaExp exp4 ;
		node.getExp4().apply(this);
		exp4 = (SaExp) returnValue;
		returnValue = exp4;
	}

	@Override
	public void caseAAdditionExp3(AAdditionExp3 node){
		inAAdditionExp3(node);
	}

	@Override
	public void caseASoustractionExp3(ASoustractionExp3 node){
		inASoustractionExp3(node);
	}

	@Override
	public void caseAExp5Exp4(AExp5Exp4 node){
		inAExp5Exp4(node);
		SaExp exp5 ;
		node.getExp5().apply(this);
		exp5 = (SaExp) returnValue;
		returnValue = exp5;
	}

	@Override
	public void caseAMultiplicationExp4(AMultiplicationExp4 node){
		inAMultiplicationExp4(node);
	}


	@Override
	public void caseADivisionExp4(ADivisionExp4 node){
		inADivisionExp4(node);
	}

	@Override
	public void caseAExp6Exp5(AExp6Exp5 node){
		inAExp6Exp5(node);
		SaExp exp6 ;
		node.getExp6().apply(this);
		exp6 = (SaExp) returnValue;
		returnValue = exp6;
	}

	@Override
	public void caseANonLogiqueExp5(ANonLogiqueExp5 node){
		inANonLogiqueExp5(node);
	}

	@Override
	public void caseAParentheseExp6(AParentheseExp6 node){
		inAParentheseExp6(node);
	}

	@Override
	public void caseANombrerExp6(ANombrerExp6 node){
		inANombrerExp6(node);
	}

	@Override
	public void caseAAplFoncExp6(AAplFoncExp6 node){
		inAAplFoncExp6(node);
	}

	@Override
	public void caseAVariableExp6(AVariableExp6 node){
		inAVariableExp6(node);
	}

	@Override
	public void caseAChiffresNombre(AChiffresNombre node){
		inAChiffresNombre(node);
	}

	@Override
	public void caseAChiffreNombre(AChiffreNombre node){
		inAChiffreNombre(node);
	}

	@Override
	public void caseAAffectInstruction(AAffectInstruction node){
		inAAffectInstruction(node);
	}

	@Override
	public void caseAIfInstruction(AIfInstruction node){
		inAIfInstruction(node);
	}

	@Override
	public void caseAWhileInstruction(AWhileInstruction node){
		inAWhileInstruction(node);
	}

	@Override
	public void caseAReturnInstruction(AReturnInstruction node){
		inAReturnInstruction(node);
	}

	@Override
	public void caseAAplFoncInstruction(AAplFoncInstruction node){
		inAAplFoncInstruction(node);
	}

	@Override
	public void caseABlockInstructionsInstruction(ABlockInstructionsInstruction node){
		inABlockInstructionsInstruction(node);
	}

	@Override
	public void caseADeclaSiSinonCompl(ADeclaSiSinonCompl node){
		inADeclaSiSinonCompl(node);
	}

	@Override
	public void caseAVideCompl(AVideCompl node){
		inAVideCompl(node);
	}

	@Override
	public void caseABloqueBlocInst(ABloqueBlocInst node){
		inABloqueBlocInst(node);
	}

	@Override
	public void caseAListListInst(AListListInst node){
		inAListListInst(node);
	}

	@Override
	public void caseAVideListInst(AVideListInst node){
		inAVideListInst(node);
	}

	@Override
	public void caseASousListSousListInst(ASousListSousListInst node){
		inASousListSousListInst(node);
	}

	@Override
	public void caseAVideSousListInst(AVideSousListInst node){
		inAVideSousListInst(node);
	}

	@Override
	public void caseADeclarationDeclaVariable(ADeclarationDeclaVariable node){
		inADeclarationDeclaVariable(node);
	}

	@Override
	public void caseADeclaSuivDeclaVariable(ADeclaSuivDeclaVariable node){
		inADeclaSuivDeclaVariable(node);
	}

	@Override
	public void caseADeclaSeuleDeclaVariable(ADeclaSeuleDeclaVariable node){
		inADeclaSeuleDeclaVariable(node);
	}

	@Override
	public void caseAVarVariable(AVarVariable node){
		inAVarVariable(node);
	}

	@Override
	public void caseATabVariable(ATabVariable node){
		inATabVariable(node);
	}

	@Override
	public void caseADeclaFncFonction(ADeclaFncFonction node){
		inADeclaFncFonction(node);
	}

	@Override
	public void caseADeclaAplFoncAplFonc(ADeclaAplFoncAplFonc node){
		inADeclaAplFoncAplFonc(node);
	}

	@Override
	public void caseALecAplFonc(ALecAplFonc node){
		inALecAplFonc(node);
	}

	@Override
	public void caseAEcrAplFonc(AEcrAplFonc node){
		inAEcrAplFonc(node);
	}

	@Override
	public void caseADeclaLecLecture(ADeclaLecLecture node){
		inADeclaLecLecture(node);
	}

	@Override
	public void caseADeclaEcriEcriture(ADeclaEcriEcriture node){
		inADeclaEcriEcriture(node);
	}

	public SaNode getRoot() {
		return this.returnValue;
	}

}
