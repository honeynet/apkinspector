LOG=log

GOOD =  \
	ArrayInit.jl \
	Conditional1.jl \
	ConformanceCheck3.jl \
	Constants.jl \
	Constants2.jl \
	Constants3.jl \
	Constants4.jl \
	Constants5.jl \
	Constants6.jl \
	Constants7.jl \
	Constants8.jl \
	Ctor.jl \
	Dep1.jl \
	Empty.jl \
	Equality.jl \
	Equality2.jl \
	Except.jl \
	Except2.jl \
	Except3.jl \
	Except4.jl \
	Except5.jl \
	Except6.jl \
	Field.jl \
	FieldAccess.jl \
	FinalInit1.jl \
	FinalInit2.jl \
	FinalInit3.jl \
	FinalInit4.jl \
	FinalInit5.jl \
	FinalInit6.jl \
	FinalInit7.jl \
	ForInit.jl \
	ForwardRef.jl \
	ForwardRef2.jl \
	ForwardRef3.jl \
	Increment.jl \
	Infinite.jl \
	Init1.jl \
	Init2.jl \
	Init3.jl \
	Init4.jl \
	Init5.jl \
	InitializerType1.jl \
	Inner0.jl \
	Inner1.jl \
	Inner2.jl \
	Inner3.jl \
	Inner4.jl \
	Inner5.jl \
	Inner6.jl \
	Inner7.jl \
	Inner8.jl \
	Inner9.jl \
	InterfaceOverride.jl \
	LabeledBreak.jl \
	Local.jl \
	LocalClass.jl \
	LocalClass2.jl \
	LocalClass3.jl \
	LocalClass4.jl \
	LocalInit.jl \
	MemberClassFlags.jl \
	Narrowing.jl \
	Prim.jl \
	Return1.jl \
	Return2.jl \
	Return3.jl \
	Return4.jl \
	Return5.jl \
	Semicolon.jl \
	SemicolonClass.jl \
	Shadowing.jl \
	Statements.jl \
	StaticContext.jl \
	StaticOps.jl \
	Switch1.jl \
	Switch2.jl \
	Switch3.jl \
	Switch4.jl \
	Switch5.jl \
	TypeShadowing.jl \
	xIterator.jl

BAD = \
	BadAbstract.jl \
	BadAlloc.jl \
	BadCtor.jl \
	BadExcept.jl \
	BadExcept2.jl \
	BadExpCC.jl \
	BadExtends.jl \
	BadFinalInit1.jl \
	BadFinalInit10.jl \
	BadFinalInit11.jl \
	BadFinalInit12.jl \
	BadFinalInit13.jl \
	BadFinalInit2.jl \
	BadFinalInit3.jl \
	BadFinalInit4.jl \
	BadFinalInit5.jl \
	BadFinalInit6.jl \
	BadFinalInit7.jl \
	BadFinalInit8.jl \
	BadFinalInit9.jl \
	BadForwardRef.jl \
	BadIncrement1.jl \
	BadIncrement2.jl \
	BadIncrement3.jl \
	BadIncrement4.jl \
	BadInit.jl \
	BadInner.jl \
	BadInner2.jl \
	BadInner3.jl \
	BadInner4.jl \
	BadInner5.jl \
	BadInner6.jl \
	BadInner7.jl \
	BadInterface.jl \
	BadInterface2.jl \
	BadMultipleVarDef.jl \
	BadOverride.jl \
	BadOverride2.jl \
	BadPrim.jl \
	BadProt.jl \
	BadStaticContext.jl \
	BadSwitch1.jl \
	BadSwitch2.jl \
	CircularConstructor.jl \
	ConformanceCheck1.jl \
	ConformanceCheck2.jl \
	DoubleFlags.jl \
	Errors.jl \
	Errors2.jl \
	ForwardRef4.jl \
	LabeledBreak2.jl \
	LocalClass5.jl \
	NoInit1.jl \
	NoInit10.jl \
	NoInit11.jl \
	NoInit12.jl \
	NoInit2.jl \
	NoInit3.jl \
	NoInit4.jl \
	NoInit5.jl \
	NoInit6.jl \
	NoInit7.jl \
	NoInit8.jl \
	NoInit9.jl \
	NoReturn1.jl \
	NoReturn2.jl \
	NoReturn3.jl \
	NoReturn4.jl \
	NoReturn5.jl \
	Protection.jl \
	Protection2.jl \
	StaticContext2.jl \
	Unreachable1.jl \
	Unreachable10.jl \
	Unreachable11.jl \
	Unreachable12.jl \
	Unreachable13.jl \
	Unreachable14.jl \
	Unreachable15.jl \
	Unreachable16.jl \
	Unreachable2.jl \
	Unreachable3.jl \
	Unreachable4.jl \
	Unreachable5.jl \
	Unreachable6.jl \
	Unreachable7.jl \
	Unreachable8.jl \
	Unreachable9.jl

.SUFFIXES: .jl .java

GOOD_OUTPUT = $(patsubst %.jl,%.java,$(GOOD)) Dep2.java Dep3.java
BAD_OUTPUT = $(patsubst %.jl,%.java,$(BAD))

all: good bad

good: $(GOOD)
	-rm -f $(GOOD_OUTPUT) good.log
	cat /dev/null > good.log
	$(MAKE) LOG=good.log $(GOOD_OUTPUT)
	-(cd disamb_member; rm -f *.class)
	-(cd recursive_serialize; rm -f *.class)
	-(cd package1; rm -f *.class)
	-(cd package2; rm -f *.class)
	(cd disamb_member; jlc -noserial CollectionsTester.jl) >> good.log 2>&1
	jlc package1/InnerClassAccess.jl >> good.log 2>&1
	jlc package2/ProtectedCtorAccess.jl >> good.log 2>&1
	(cd recursive_serialize; jlc Main.jl) >> good.log 2>&1
	(cd recursive_serialize; jlc Main.jl) >> good.log 2>&1
	@if cmp -s good.log.in good.log; then \
	    echo "LOG UNCHANGED"; \
	    diff good.log.in good.log; \
	else \
	    echo "LOG CHANGED"; \
	    false; \
	fi

mauve:
	find gnu -name \*.jl -print -exec jlc {} \;

jacks:
	cd jacks
	# Pretend we're jikes
	./jacks jikes

bad: $(BAD)
	-rm -f $(BAD_OUTPUT) bad.log
	cat /dev/null > bad.log
	$(MAKE) LOG=bad.log $(BAD_OUTPUT)
	-(cd package1; rm -f *.class)
	-(cd package2; rm -f *.class)
	-jlc package2/BadProtectedCtorAccess.jl >> bad.log 2>&1
	@if cmp -s bad.log.in bad.log; then \
	    echo "LOG UNCHANGED"; \
	    diff bad.log.in bad.log; \
	else \
	    echo "LOG CHANGED"; \
	    false; \
	fi

clean:
	-rm -f $(GOOD_OUTPUT)
	-rm -f $(BAD_OUTPUT)
	-rm -f disamb_member/*.class
	-rm -f recursive_serialize/*.class
	-rm -f disamb_member/*.java
	-rm -f recursive_serialize/*.java
	-rm -f *.class
	-rm -f good.log bad.log

.jl.java:
	@-rm -f *.class
	@-echo jlc $<
	@-jlc $< 2>&1 | sed 's%^.*/\([A-Za-z0-9_]*\.java:\)%\1%g' | tee -a $(LOG)
