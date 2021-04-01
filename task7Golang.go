package main

import (
	"fmt"
	"sync"
	"time"
)

func Tunnel_1(trainLeft chan int, done chan int) {
	for {
		fmt.Println("First tunnel is free")
		<-trainLeft
		time.Sleep(time.Second)
		fmt.Println("Train coming through first tunnel")
		time.Sleep(time.Second * 3)
		fmt.Println("First tunnel is used")
		done <- 0
	}
}
func Tunnel_2(trainRight chan int, done chan int) {
	for {
		fmt.Println("Second tunnel is free")
		<-trainRight
		time.Sleep(time.Second)
		fmt.Println("Train coming through second tunnel")
		time.Sleep(time.Second * 3)
		fmt.Println("Second tunnel is used")
		done <- 0
	}
}

func TrainsL(trainLeft chan int, done chan int, wg *sync.WaitGroup, ind int) {
	time.Sleep(time.Second)
	fmt.Println("Train " + string(ind) + " driving to the left is coming")
	trainLeft <- ind
	var i = <-done
	fmt.Println("Train " + string(i) + " driving to the left left the tunnel")
	wg.Done()
}

func TrainsR(trainRight chan int, done chan int, wg *sync.WaitGroup, ind int) {
	time.Sleep(time.Second)
	fmt.Println("Train " + string(ind) + " driving to the right is coming")
	trainRight <- ind
	var i = <-done
	fmt.Println("Train " + string(i) + " driving to the right left the tunnel")
	wg.Done()
}
func main() {
	var trainLeft = make(chan int)
	var trainRight = make(chan int)
	var done = make(chan int)

	var wg1 sync.WaitGroup
	wg1.Add(3)

	var wg2 sync.WaitGroup
	wg2.Add(3)
	go Tunnel_1(trainLeft, done)
	go TrainsL(trainLeft, done, &wg1, 2)

	go Tunnel_2(trainRight, done)
	go TrainsR(trainRight, done, &wg2, 3)
	wg1.Wait()
	wg2.Wait()
}
