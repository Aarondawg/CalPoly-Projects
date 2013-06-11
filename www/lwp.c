#include <stdio.h>
#include "lwp.h"
#include <stdlib.h>

static schedfun schedulingFunction;
static int nextPID = 1;
lwp_context *lwp_ptable;
int lwp_procs = 0;
int lwp_running;

int new_lwp(lwpfun function, void *argument, size_t stacksize)
{
    // First LWP being allocated, malloc process table 
    if (lwp_procs == 0) {
        lwp_ptable = malloc( sizeof(lwp_context) * LWP_PROC_LIMIT );
        lwp_procs = 0;
        lwp_running = 0;
    }

    // Too many LWPs, return -1
    if (nextPID > LWP_PROC_LIMIT)
        return -1;

    // Set PID and increment for next new_lwp call
    lwp_ptable[lwp_procs].pid = nextPID;
    nextPID++;

    // Set up stack for new LWP.  Check my math on calculating stackpointer
    lwp_ptable[lwp_procs].stack = malloc(stacksize * 4);
    lwp_ptable[lwp_procs].stacksize = stacksize;
    lwp_ptable[lwp_procs].sp = 
	    lwp_ptable[lwp_procs].stack - ((stacksize * 4) / sizeof(long));

    // Increment number of LWPs and return the PID of this LWP
    return lwp_ptable[lwp_procs++].pid;
}

void lwp_yield()
{
    SAVE_STATE();
    GetSP(lwp_ptable[lwp_running].sp);

    // Choose new LWP
    if (schedulingFunction != NULL) {
        lwp_running = schedulingFunction();
    }

    // Round robin scheduling increments index of running LWP and restores stack
    else {
        if (lwp_running < lwp_procs - 1)
            lwp_running++;
      
        else
            lwp_running = 0;
    }

    // Restore state of selected LWP
    SetSP(lwp_ptable[lwp_running].sp);
    RESTORE_STATE();
}



/*
unsigned long lwp_getpid()
{
    return lwp_ptable[lwp_running].pid;
}
*/
