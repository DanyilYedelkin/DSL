#include "common.h"

void state_idle();
void state_active();
void state_waitingForLight();
void state_waitingForDrawer();
void state_unlockedPanel();

void state_idle() {
	send_command('D');
	send_command('L');
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'd':
			return state_active();
		case 'o':
			return state_idle();
		}
	}
}

void state_active() {
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'w':
			return state_waitingForLight();
		case 'l':
			return state_waitingForDrawer();
		case 'o':
			return state_idle();
		}
	}
}

void state_waitingForLight() {
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'l':
			return state_unlockedPanel();
		case 'o':
			return state_idle();
		}
	}
}

void state_waitingForDrawer() {
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'w':
			return state_unlockedPanel();
		case 'o':
			return state_idle();
		}
	}
}

void state_unlockedPanel() {
	send_command('U');
	send_command('C');
	char ev;
	while (ev = read_event()) {
		switch (ev) {
		case 'p':
			return state_idle();
		case 'o':
			return state_idle();
		}
	}
}

void main() {
	state_idle();
}