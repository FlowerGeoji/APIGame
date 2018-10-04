//
//  Game.swift
//  APIGameIOS
//
//  Created by FlowerGeoji on 2018. 10. 4..
//  Copyright © 2018년 FlowerGeoji. All rights reserved.
//

import Foundation
import UIKit

protocol GameRequest: class {
  func onRequestApi(_ method: String, _ url: String, _ data: Any?, _ completion: @escaping (Bool, [String:Any]?) -> ())
}

class Game: UIView, UITableViewDelegate, UITableViewDataSource {
  enum Module: Int {
    case OX = 0
    case CHOICE
    case SUBJECTIVE
    case SURVIVAL
    case JAM
  }
  
  enum Role {
    case HOST
    case GUEST
    case SETTING
  }
  
  public let role: Role
  private(set) var module: Module? {
    didSet {
      self.didSetModule()
    }
  }
  public weak var delegate: GameRequest?
  
  public let roomId: Int
  private(set) var gameId: Int?
  
  let gameListView: UIView = UIView()
  let gameListTableView: UITableView = UITableView()
  
  // init
  required init?(coder aDecoder: NSCoder) {
    super.init(coder: aDecoder)
  }
  
  init(role: Role, roomId: Int) {
    self.role = role
    self.roomId = roomId
    self.initializeView()
  }
  
  func initializeView() {
    if self.role == Game.Role.HOST {
      // init view
      self.addSubview(gameListView)
      self.gameListView.translatesAutoresizingMaskIntoConstraints = false
      self.gameListView.topAnchor.constraint(equalTo: self.topAnchor).isActive = true
      self.gameListView.leadingAnchor.constraint(equalTo: self.leadingAnchor).isActive = true
      self.gameListView.trailingAnchor.constraint(equalTo: self.trailingAnchor).isActive = true
      self.gameListView.bottomAnchor.constraint(equalTo: self.bottomAnchor).isActive = true
      
      self.gameListTableView.dataSource = self
      self.gameListTableView.delegate = self
      self.gameListTableView.register(GameCell.self, forCellReuseIdentifier: "GameCell")
      self.gameListTableView.allowsMultipleSelection = false
      self.gameListView.addSubview(self.gameListTableView)
      self.gameListTableView.translatesAutoresizingMaskIntoConstraints = false
      self.gameListTableView.topAnchor.constraint(equalTo: self.gameListView.topAnchor).isActive = true
      self.gameListTableView.leadingAnchor.constraint(equalTo: self.gameListView.leadingAnchor).isActive = true
      self.gameListTableView.trailingAnchor.constraint(equalTo: self.gameListView.trailingAnchor).isActive = true
      self.gameListTableView.bottomAnchor.constraint(equalTo: self.gameListView.bottomAnchor).isActive = true
    }
  }
  
  // func
  private func selectGame(module: Module) {
    self.module = module
  }
  
  private func didSetModule() {
    guard let module = self.module else {
      if self.gameListView.isHidden {
        self.gameListView.isHidden = false
      }
      return
    }
    
    self.gameListView.isHidden = true
    switch module {
    case .OX:
      break
    case .CHOICE:
      break
    case .SUBJECTIVE:
      break
    case .SURVIVAL:
      break
    case .JAM:
      break
    }
  }
  
  func requestApi(_ method: String, _ url: String, _ data: Any?, _ completion: @escaping (Bool, [String: Any]?) -> ()) {
    guard let delegate = self.delegate else {
      return
    }
    delegate.onRequestApi(method, url, data, completion)
  }
  
  // tableView
  class GameCell: UITableViewCell {
    private(set) var module: Module!
    let labelTitle: UILabel = UILabel()
    let labelDescription: UILabel = UILabel()
    
    func initialize(module: Module) {
      self.module = module
      self.contentView.addSubview(labelTitle)
      self.contentView.addSubview(labelDescription)
    }
  }
  
  func numberOfSections(in tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return 5
  }
  
  func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    return UITableView.automaticDimension
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    guard let cell = tableView.dequeueReusableCell(withIdentifier: "GameCell") as? GameCell else {
      return UITableViewCell()
    }
    
    cell.initialize(module: Game.Module.init(rawValue: indexPath.row)!)
    return cell
  }
  
  func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
    if let cell = tableView.cellForRow(at: indexPath) as? GameCell{
      self.selectGame(module: cell.module)
    }
  }
}
