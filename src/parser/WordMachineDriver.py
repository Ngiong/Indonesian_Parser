from src.parser.WordMachine import WordMachine

word_machine = WordMachine("(CP (SYM \")(CCN Jika)(SBAR (NP (NNO DUP))(VP (NEG tidak)(VP (VBI siap)(PP (PPO untuk)(VP (VP (VBI bekerja)(CCN atau)(VP (VBT memberi)(NP (NNO indikasi)(ADJ positif)(ADJ substantif))))(PP (PPO pada)(NNO Juni)))))))(SYM ,))(CP (CCN maka)(SBAR (NP (NNO Pemerintahan)(NNP Inggris))(AdvP (ADV dapat)(VP (VP (VBT menyetop)(NNO gaji))(PP (PPO pada)(NP (NNO akhir)(NNO Juni))))))(SYM ,)(SYM \"))(NP (VBI kata)(NP (NNP Martin_McGuinnes)(PP (PPO dari)(NP (NNP Sinn)(NNP Fein)))))(SYM .))")
while word_machine.has_remaining():
    print('#'+word_machine.next_word()+'#')