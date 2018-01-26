from src.parser.ParseTree import ParseTree

test_str = 'S (SBAR (NP (NP (NP (NNO Pembuat)(NNO PC))(CCN dan)(NP (NNO kompetitor)(NP (NNP Apple)(NNP Computer))))(NNP Dell_,_Inc))(VP (VBT menyatakan)(CP (CCN bahwa)(VP (VBL adalah)(AdjP (VBT menarik)(VP (VBT mengirimkan)(NP (NNO komputer)(RPN (PRR yang)(VP (VBT menjalankan)(NP (NP (NNP Mac)(NNO OS)(NUM X))(NP (NNO milik)(NNP Apple)))))))))))(SYM .))(SBAR (NP (NNP Michael_Dell)(SYM ,)(NP (NP (NNO pendiri)(CCN dan)(NNO chairman))(NP (NNP Dell)(NNP Computer)))(SYM ,))(VP (VP (VBT membuat)(NNO komentar))(CP (CCN ketika)(VP (VBI berbicara)(PP (PPO dengan)(NP (NNP David_Kirkpatrick)(PP (PPO dari)(NP (NNO majalah)(NNP Fortuner)))))))))(SYM .)'
pt = ParseTree(test_str)
pt.makeTree()
pt.printTree()

check_result = pt.checkTree()
if check_result:
    print('VERIFIED')
else:
    print('NOT VERIFIED')