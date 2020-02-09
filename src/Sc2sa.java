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
		inAMainMainFonc(node);
		String nom;
		SaLDec var;
		SaInst inst;
		node.getMain().apply(this);
		nom =  returnValue.toString();
		node.getOptdecvar().apply(this);
		var = (SaLDec) returnValue;
		node.getBlocInst().apply(this);
		inst = (SaInst) returnValue;
		returnValue = new SaDecFonc(nom,null,var,inst);
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
		SaExp exp3 ;
		SaExp exp4 ;
		node.getExp3().apply(this);
		exp3 = (SaExp) returnValue;
		node.getExp4().apply(this);
		exp4= (SaExp) returnValue;
		returnValue = new SaExpAdd(exp3,exp4);
	}

	@Override
	public void caseASoustractionExp3(ASoustractionExp3 node){
		inASoustractionExp3(node);
		SaExp exp3 ;
		SaExp exp4 ;
		node.getExp3().apply(this);
		exp3 = (SaExp) returnValue;
		node.getExp4().apply(this);
		exp4= (SaExp) returnValue;
		returnValue = new SaExpSub(exp3,exp4);
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
		SaExp exp4 ;
		SaExp exp5 ;
		node.getExp4().apply(this);
		exp4 = (SaExp) returnValue;
		node.getExp5().apply(this);
		exp5= (SaExp) returnValue;
		returnValue = new SaExpAdd(exp4,exp5);
	}


	@Override
	public void caseADivisionExp4(ADivisionExp4 node){
		inADivisionExp4(node);
		SaExp exp4 ;
		SaExp exp5 ;
		node.getExp4().apply(this);
		exp4 = (SaExp) returnValue;
		node.getExp5().apply(this);
		exp5= (SaExp) returnValue;
		returnValue = new SaExpDiv(exp4,exp5);
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
		SaExp exp5;
		node.getExp6().apply(this);
		exp5 = (SaExp) returnValue;
		returnValue = new SaExpNot(exp5);
	}

	@Override
	public void caseAParentheseExp6(AParentheseExp6 node){
		inAParentheseExp6(node);
		SaExp exp6 ;
		node.getExpr().apply(this);
		exp6 = (SaExp) returnValue;
		returnValue = exp6;
	}

	@Override
	public void caseANombrerExp6(ANombrerExp6 node){
		inANombrerExp6(node);
		int exp6;
		exp6 = Integer.parseInt(node.getNombre().toString());
		returnValue = new SaExpInt(exp6);
	}

	@Override
	public void caseAAplFoncExp6(AAplFoncExp6 node){
		inAAplFoncExp6(node);
		SaAppel appel;
		node.getAplFonc().apply(this);
		appel = (SaAppel) returnValue;
		returnValue = appel;
	}

	@Override
	public void caseAVariableExp6(AVariableExp6 node){
		inAVariableExp6(node);
		SaVar var;
		node.getVariable().apply(this);
		var = (SaVar) returnValue;
		returnValue = new SaExpVar(var);
	}

	@Override
	public void caseAChiffresNombre(AChiffresNombre node){
		inAChiffresNombre(node);
		String var;
		SaExp vars;
		node.getChiffre().apply(this);
		var =  returnValue.toString();
		node.getNombre().apply(this);
		vars = (SaExp) returnValue;
		returnValue = new SaVarIndicee(var,vars);
	}

	@Override
	public void caseAChiffreNombre(AChiffreNombre node){
		inAChiffreNombre(node);
		String var;
		node.getChiffre().apply(this);
		var =  returnValue.toString();
		returnValue = new SaVarSimple(var);
	}

	@Override
	public void caseAAffectInstruction(AAffectInstruction node){
		inAAffectInstruction(node);
		SaInstAffect affect ;
		node.getEgale().apply(this);
		affect = (SaInstAffect) returnValue;
		returnValue = affect;
	}

	@Override
	public void caseAIfInstruction(AIfInstruction node){
		inAIfInstruction(node);
		SaInstSi Si ;
		node.getSi().apply(this);
		Si = (SaInstSi) returnValue;
		returnValue = Si;
	}

	@Override
	public void caseAWhileInstruction(AWhileInstruction node){
		inAWhileInstruction(node);
		SaInstTantQue Tantque ;
		node.getTantQueLogique().apply(this);
		Tantque = (SaInstTantQue) returnValue;
		returnValue = Tantque;
	}

	@Override
	public void caseAReturnInstruction(AReturnInstruction node){
		inAReturnInstruction(node);
		SaInstRetour retour;
		node.getRetourner().apply(this);
		retour = (SaInstRetour) returnValue;
		returnValue = retour;
	}

	@Override
	public void caseAAplFoncInstruction(AAplFoncInstruction node){
		inAAplFoncInstruction(node);
		SaAppel fnc;
		node.getAplFonc().apply(this);
		fnc = (SaAppel) returnValue;
		returnValue = fnc;
	}

	@Override
	public void caseABlockInstructionsInstruction(ABlockInstructionsInstruction node){
		inABlockInstructionsInstruction(node);
		SaInstBloc bloc;
		node.getBlocInst().apply(this);
		bloc = (SaInstBloc) returnValue;
		returnValue = bloc;
	}

	@Override
	public void caseADeclaSiSinonCompl(ADeclaSiSinonCompl node){
		inADeclaSiSinonCompl(node);
		SaDec sinon;
		SaLDec bloc;
		node.getSinon().apply(this);
		sinon = (SaDec) returnValue;
		node.getBlocInst().apply(this);
		bloc = (SaLDec) returnValue;
		returnValue = new SaLDec(sinon,bloc);
	}

	@Override
	public void caseAVideCompl(AVideCompl node){
		inAVideCompl(node);
		returnValue = null;
	}

	@Override
	public void caseABloqueBlocInst(ABloqueBlocInst node){
		inABloqueBlocInst(node);
		SaInstBloc bloc;
		node.getListInst().apply(this);
		bloc = (SaInstBloc) returnValue;
		returnValue = bloc;
	}

	@Override
	public void caseAListListInst(AListListInst node){
		inAListListInst(node);
		SaInst inst;
		SaLInst Linst;
		node.getInstruction().apply(this);
		inst = (SaInst) returnValue;
		node.getSousListInst().apply(this);
		Linst = (SaLInst) returnValue;
		returnValue = new SaLInst(inst,Linst);
	}

	@Override
	public void caseAVideListInst(AVideListInst node){
		inAVideListInst(node);
		returnValue = null;
	}

	@Override
	public void caseASousListSousListInst(ASousListSousListInst node){
		inASousListSousListInst(node);
		node.getInstruction().apply(this);
		node.getSousListInst().apply(this);
	}

	@Override
	public void caseAVideSousListInst(AVideSousListInst node){
		inAVideSousListInst(node);
		returnValue = null;
	}

	@Override
	public void caseADeclarationDeclaVariable(ADeclarationDeclaVariable node){
		inADeclarationDeclaVariable(node);
		String nom;
		node.getVariable().apply(this);
		nom = returnValue.toString();
		returnValue = new SaDecVar(nom);
	}

	@Override
	public void caseADeclaSuivDeclaVariable(ADeclaSuivDeclaVariable node){
		inADeclaSuivDeclaVariable(node);
		SaDec var;
		SaLDec vars;
		node.getVariable().apply(this);
		var = (SaDec) returnValue;
		node.getDeclaVariable().apply(this);
		vars = (SaLDec) returnValue;
		returnValue = new SaLDec(var,vars);
	}

	@Override
	public void caseADeclaSeuleDeclaVariable(ADeclaSeuleDeclaVariable node){
		inADeclaSeuleDeclaVariable(node);
		SaDec var;
		node.getVariable().apply(this);
		var = (SaDec) returnValue;
		returnValue = new SaLDec(var,null);
	}

	@Override
	public void caseAVarVariable(AVarVariable node){
		inAVarVariable(node);
		node.getNom().apply(this);
	}

	@Override
	public void caseATabVariable(ATabVariable node){
		inATabVariable(node);
		node.getNom().apply(this);
		node.getNombre().apply(this);
	}

	@Override
	public void caseADeclaFncFonction(ADeclaFncFonction node){
		inADeclaFncFonction(node);
		node.getNom().apply(this);
		node.getDeclaVariable().apply(this);
		node.getOptdecvar().apply(this);
		node.getBlocInst().apply(this);
	}

	@Override
	public void caseADeclaAplFoncAplFonc(ADeclaAplFoncAplFonc node){
		inADeclaAplFoncAplFonc(node);
		node.getNom().apply(this);
		node.getListExpr().apply(this);
	}

	@Override
	public void caseALecAplFonc(ALecAplFonc node){
		inALecAplFonc(node);
		node.getLecture().apply(this);

	}

	@Override
	public void caseAEcrAplFonc(AEcrAplFonc node){
		inAEcrAplFonc(node);
		node.getEcriture().apply(this);
	}

	@Override
	public void caseADeclaLecLecture(ADeclaLecLecture node){
		inADeclaLecLecture(node);
		node.getLire().apply(this);
	}

	@Override
	public void caseADeclaEcriEcriture(ADeclaEcriEcriture node){
		inADeclaEcriEcriture(node);
		node.getEcrire().apply(this);
		node.getExpr().apply(this);
	}

	public SaNode getRoot() {
		return this.returnValue;
	}

}
