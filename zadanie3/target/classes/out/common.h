#include <stdio.h>

char read_event() {
    return getc(stdin);
}

void send_command(char c) {
    putc(c, stdout);
    putc('\n', stdout);
}

