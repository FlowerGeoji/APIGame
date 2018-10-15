//
//  ViewController.swift
//  APIGameIOSTester
//
//  Created by FlowerGeoji on 2018. 10. 15..
//  Copyright © 2018년 FlowerGeoji. All rights reserved.
//

import UIKit
import APIGameIOS

class ViewController: UIViewController {

  override func viewDidLoad() {
    super.viewDidLoad()
    // Do any additional setup after loading the view, typically from a nib.
    let game: Game = Game(role: Game.Role.HOST, roomId: 0)
  }


}

