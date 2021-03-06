import SwiftUI
import ios_kotlin_pod
import lib_basic_swift
import app_di_swift
import Foundation

struct GlobalContentView: View {

    let appDiIos = AppDiIos()
    @ObservedObject var myViewModel:GlobalViewModel

    init() {
        self.myViewModel = GlobalViewModel(di: appDiIos.common)
    }

    var body: some View {
        VStack {
            //Text("updateCount: \(myViewModel.myState.updateCount)")
            appDiIos.solutionTabs.renderScaffold()
        }
    }

}
