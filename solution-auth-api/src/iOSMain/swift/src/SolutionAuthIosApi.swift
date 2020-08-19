import SwiftUI
import Foundation
import app_di
import lib_basic_swift
public protocol SolutionAuthIosApi {
    associatedtype V1: View
    associatedtype V2: View
    func renderLoginForm() -> V1
    func renderLoginInfo() -> V2
}
