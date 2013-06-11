          .ORIG x3000

          LD   R0,G
          LD   R1,M
          TRAP x26

          HALT

G    .FILL x47
M    .FILL x4D

          .END
