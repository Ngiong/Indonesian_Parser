from src.parser.LineMachine import LineMachine

lm = LineMachine('../rekap.txt')
print(lm.next_line(), end='')
print(lm.next_line(), end='')
print(lm.next_line(), end='')
print(lm.next_line(), end='')
lm.terminate()