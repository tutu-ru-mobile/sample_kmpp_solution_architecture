import SwiftUI
import Foundation
import app_di

public protocol SolutionWalletIosApi {
    associatedtype V1: View
    associatedtype V2: View
    func renderWalletAndRefillButton() -> V1
    func renderWallet() -> V2
}
