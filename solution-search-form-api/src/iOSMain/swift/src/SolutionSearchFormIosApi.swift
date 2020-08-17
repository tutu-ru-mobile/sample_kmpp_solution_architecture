import SwiftUI
import Foundation
import app_di

public protocol SolutionSearchFormIosApi {
    associatedtype V1: View
    func todoRender() -> V1
}