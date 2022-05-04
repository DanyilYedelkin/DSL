#include "common.h"

void state_s1();
void state_s2();

void state_s1() {
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'n':
			return state_s2();
		case 'r':
			return state_s1();
		}
	}
}

void state_s2() {
	send_command('X');
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'r':
			return state_s1();
		}
	}
}

void main() {
	state_s1();
}