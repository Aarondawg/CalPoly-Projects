	.file	"lwp.c"
	.local	schedulingFunction
	.comm	schedulingFunction,4,4
	.data
	.align 4
	.type	nextPID, @object
	.size	nextPID, 4
nextPID:
	.long	1
	.text
.globl new_lwp
	.type	new_lwp, @function
new_lwp:
	pushl	%ebp
	movl	%esp, %ebp
	pushl	%ebx
	subl	$20, %esp
	movl	lwp_ptable, %eax
	testl	%eax, %eax
	jne	.L2
	movl	$480, (%esp)
	call	malloc
	movl	%eax, lwp_ptable
	movl	$0, lwp_procs
	movl	$0, lwp_running
.L2:
	movl	nextPID, %eax
	cmpl	$30, %eax
	jle	.L3
	movl	$-1, %eax
	jmp	.L4
.L3:
	movl	lwp_ptable, %eax
	movl	lwp_procs, %edx
	sall	$4, %edx
	leal	(%eax,%edx), %edx
	movl	nextPID, %eax
	movl	%eax, (%edx)
	movl	nextPID, %eax
	addl	$1, %eax
	movl	%eax, nextPID
	movl	lwp_ptable, %eax
	movl	lwp_procs, %edx
	sall	$4, %edx
	leal	(%eax,%edx), %ebx
	movl	16(%ebp), %eax
	sall	$2, %eax
	movl	%eax, (%esp)
	call	malloc
	movl	%eax, 4(%ebx)
	movl	lwp_ptable, %eax
	movl	lwp_procs, %edx
	sall	$4, %edx
	leal	(%eax,%edx), %edx
	movl	16(%ebp), %eax
	movl	%eax, 8(%edx)
	movl	lwp_ptable, %eax
	movl	lwp_procs, %edx
	sall	$4, %edx
	addl	%edx, %eax
	movl	lwp_ptable, %edx
	movl	lwp_procs, %ecx
	sall	$4, %ecx
	addl	%ecx, %edx
	movl	4(%edx), %edx
	movl	16(%ebp), %ecx
	sall	$2, %ecx
	shrl	$2, %ecx
	sall	$2, %ecx
	negl	%ecx
	addl	%ecx, %edx
	movl	%edx, 12(%eax)
	movl	lwp_ptable, %eax
	movl	lwp_procs, %edx
	movl	%edx, %ecx
	sall	$4, %ecx
	addl	%ecx, %eax
	movl	(%eax), %eax
	addl	$1, %edx
	movl	%edx, lwp_procs
.L4:
	addl	$20, %esp
	popl	%ebx
	popl	%ebp
	ret
	.size	new_lwp, .-new_lwp
.globl lwp_yield
	.type	lwp_yield, @function
lwp_yield:
	pushl	%ebp
	movl	%esp, %ebp
	subl	$8, %esp
#APP
# 37 "lwp.c" 1
	pushl %eax
# 0 "" 2
# 37 "lwp.c" 1
	pushl %ebx
# 0 "" 2
# 37 "lwp.c" 1
	pushl %ecx
# 0 "" 2
# 37 "lwp.c" 1
	pushl %edx
# 0 "" 2
# 37 "lwp.c" 1
	pushl %esi
# 0 "" 2
# 37 "lwp.c" 1
	pushl %edi
# 0 "" 2
# 37 "lwp.c" 1
	pushl %ebp
# 0 "" 2
#NO_APP
	movl	lwp_ptable, %eax
	movl	lwp_running, %edx
	sall	$4, %edx
	leal	(%eax,%edx), %edx
#APP
# 38 "lwp.c" 1
	movl  %esp,%eax
# 0 "" 2
#NO_APP
	movl	%eax, 12(%edx)
	movl	schedulingFunction, %eax
	testl	%eax, %eax
	je	.L7
	movl	schedulingFunction, %eax
	call	*%eax
	movl	%eax, lwp_running
	jmp	.L8
.L7:
	movl	lwp_procs, %eax
	leal	-1(%eax), %edx
	movl	lwp_running, %eax
	cmpl	%eax, %edx
	jle	.L9
	movl	lwp_running, %eax
	addl	$1, %eax
	movl	%eax, lwp_running
	jmp	.L8
.L9:
	movl	$0, lwp_running
.L8:
	movl	lwp_ptable, %eax
	movl	lwp_running, %edx
	sall	$4, %edx
	addl	%edx, %eax
	movl	12(%eax), %eax
#APP
# 55 "lwp.c" 1
	movl  %eax,%esp
# 0 "" 2
# 56 "lwp.c" 1
	popl  %ebp
# 0 "" 2
# 56 "lwp.c" 1
	popl  %edi
# 0 "" 2
# 56 "lwp.c" 1
	popl  %esi
# 0 "" 2
# 56 "lwp.c" 1
	popl  %edx
# 0 "" 2
# 56 "lwp.c" 1
	popl  %ecx
# 0 "" 2
# 56 "lwp.c" 1
	popl  %ebx
# 0 "" 2
# 56 "lwp.c" 1
	popl  %eax
# 0 "" 2
# 56 "lwp.c" 1
	movl  %ebp,%esp
# 0 "" 2
#NO_APP
	leave
	ret
	.size	lwp_yield, .-lwp_yield
	.ident	"GCC: (GNU) 4.4.6 20120305 (Red Hat 4.4.6-4)"
	.section	.note.GNU-stack,"",@progbits
