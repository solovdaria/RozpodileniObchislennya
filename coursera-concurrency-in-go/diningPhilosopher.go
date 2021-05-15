package main

import (
	"fmt"
	"sync"
)

type Host struct {
	sync.Mutex
	guestCount int
}

func (h *Host) addGuest() {
	h.Lock()
	h.guestCount++
	h.Unlock()
}

func (h *Host) removeGuest() {
	h.Lock()
	if h.guestCount > 0 {
		h.guestCount--
	}
	h.Unlock()

}

func (h *Host) permitNewGuest() bool {
	if h.guestCount < 2 {
		return true
	}
	return false
}

func (h *Host) getGuestCount() int {
	return h.guestCount
}

func NewHost() *Host {
	return &Host{guestCount: 0}
}

type ChopS struct{ sync.Mutex }

type Philo struct {
	leftCS, rightCS *ChopS
	eatCount        int
	i               int
}

func (p Philo) eat(h *Host) {

	for p.eatCount < 3 {

		if h.permitNewGuest() {

			h.addGuest()

			p.leftCS.Lock()
			p.rightCS.Lock()

			fmt.Println("starting to eat", p.i+1) // Philosopher numbered from 1 to 5 but indexed 0 to 4
			p.eatCount++

			fmt.Println("finishing eating", p.i+1)

			p.rightCS.Unlock()
			p.leftCS.Unlock()

			h.removeGuest()
		}
	}
	wg.Done()
}

var wg sync.WaitGroup

func main() {

	host := NewHost()

	CSticks := make([]*ChopS, 5)
	for i := 0; i < 5; i++ {
		CSticks[i] = new(ChopS)
	}
	philos := make([]*Philo, 5)
	for i := 0; i < 5; i++ {
		philos[i] = &Philo{CSticks[i], CSticks[(i+1)%5], 0, i}
	}
	for i := 0; i < 5; i++ {
		wg.Add(1)
		go philos[i].eat(host)
	}
	wg.Wait()
}
