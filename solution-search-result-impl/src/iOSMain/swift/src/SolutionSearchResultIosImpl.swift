import SwiftUI
import Foundation
import ios_kotlin_pod
import lib_basic_swift
import solution_search_result_api_swift
import solution_search_start_api_swift

public struct SolutionSearchResultIosImpl: SolutionSearchResultIosApi {

    var common: Solution_search_result_implSolutionSearchResultImpl
    var searchStart: Solution_search_start_apiSolutionSearchStartApi

    public init(
            common: Solution_search_result_implSolutionSearchResultImpl,
            searchStart: Solution_search_start_apiSolutionSearchStartApi
    ) {
        self.common = common
        self.searchStart = searchStart
    }

    public func renderSearchResult() -> some View {
        VStack {
            Text(searchStart.getSearchQuery())
            List(self.common.getState().tickets, id: \.id) { ticket in
                TicketView(item: ticket) {
                    self.common.send(action: self.common.getActionBuyTicket(ticket: ticket))
                }
            }
        }
    }

}

struct TicketView: View {
    var ticket: Entity_ticketTicket
    var clickAction: () -> Void

    public init(item: Entity_ticketTicket, clickAction: @escaping () -> ()) {
        self.ticket = item
        self.clickAction = clickAction
    }

    public var body: some View {
        HStack {
            Text(ticket.txt).font(.headline)
            Spacer().frame(width: 16)
            Button(action: { self.clickAction() }) {
                Text("Купить за \(ticket.price) р.")
            }
        }
    }
}
