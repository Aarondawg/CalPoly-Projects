/* lwp.c
 * Garrett milster and DJ Mitchell
 *
 * lwp.c handles multiple lightweight threads for processes
*/


#include <stdio.h>
#include "lwp.h"
#include <stdlib.h>

schedfun schedulingFunction = NULL;
int nextPID = 1;
lwp_context *lwp_ptable;
int lwp_procs = 0;
int lwp_running;
unsigned long *originSP;

// Spawns a new lightweight process
int new_lwp(lwpfun function, void *argument, size_t stacksize)
{

    unsigned long *tempSP;
    unsigned long *returnLoc;
    // First LWP being allocated, malloc process table 
    if (lwp_procs == 0) {
        lwp_ptable = malloc( sizeof(lwp_context) * LWP_PROC_LIMIT );
        if (schedulingFunction)
            lwp_running = schedulingFunction();
        else
            lwp_running = 0;
    }

    // Too many LWPs, return -1
    if (lwp_procs > LWP_PROC_LIMIT)
        return -1;

    // Set PID and increment for next new_lwp call
    lwp_ptable[lwp_procs].pid = nextPID;
    nextPID++;

    // Set up stack for new LWP.
    lwp_ptable[lwp_procs].stack = malloc(stacksize * 4);
    
    tempSP = lwp_ptable[lwp_procs].stack + stacksize;
    
    tempSP--;
    *tempSP = (unsigned long)argument;

    tempSP--;
    *tempSP = (unsigned long)lwp_exit;

    tempSP--;
    *tempSP = (unsigned long)function;

    returnLoc = tempSP - 1;
    // Skip over registers and BP because we don't care
    tempSP -= sizeof(unsigned long) * 8;

    *tempSP = (unsigned long)returnLoc;
   
    lwp_ptable[lwp_procs].stacksize = stacksize;

    lwp_ptable[lwp_procs].sp = tempSP;

    // Increment number of LWPs and return the PID of this LWP
    return lwp_ptable[lwp_procs++].pid;
}

// stops the current process and picks a new one
void lwp_yield()
{
    SAVE_STATE();
    GetSP(lwp_ptable[lwp_running].sp);

    // Choose new LWP by scheduling function if provided
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

// removes a lightweight process from the list
void lwp_exit()
{
    int tempIndex = lwp_running;
    int newIndex = 0;

    free(lwp_ptable[lwp_running].stack);

    // Shifts remaining ptable entries over to
    //  eliminate gap created by exiting process
    while (tempIndex < lwp_procs - 1) {
        lwp_ptable[tempIndex] = lwp_ptable[tempIndex + 1];
        tempIndex++;
    }

    // decrement number of processes
    lwp_procs--;

    // If there are no more processes, call stop
    if (!lwp_procs) {
        lwp_running = 0;
        lwp_stop();
        return;
    }

    // if its the last process in the list, set the pointer back to 
    // the beginning
    if (tempIndex == lwp_procs) {
        lwp_running = 0;
        SetSP(lwp_ptable[0].sp);
    }
    else
        SetSP(lwp_ptable[lwp_running].sp);
    
    RESTORE_STATE();  
}

// sets the scheduling function to a provided one
void lwp_set_scheduler(schedfun sched)
{
    schedulingFunction = sched;
}

int lwp_getpid()
{
    return lwp_ptable[lwp_running].pid;
}

// starts the lwp process
void lwp_start()
{
    // saves the original stack position and state
    SAVE_STATE();
    GetSP(originSP);
    
    // Choose new LWP
    if (schedulingFunction) {
        lwp_running = schedulingFunction();
    }

    SetSP(lwp_ptable[lwp_running].sp);
    RESTORE_STATE();
}

// stops the lwp process
void lwp_stop()
{
    // saves the state of the current process
    // if there are any left, and restores the
    // original stack
    int temp = lwp_running + 1;
    if (lwp_procs) {
        SAVE_STATE();
        GetSP(lwp_ptable[lwp_running].sp);  
        lwp_running = temp % lwp_procs;
    }

    SetSP(originSP);
    RESTORE_STATE();
}

