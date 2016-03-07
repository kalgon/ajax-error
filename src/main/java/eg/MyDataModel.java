package eg;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

@ManagedBean
@RequestScoped
public class MyDataModel extends LazyDataModel<MyBean> {
	
	private static final long serialVersionUID = 1L;

	private final List<MyBean> data = Arrays.asList( //
		new MyBean("John", "Kennedy"), //
		new MyBean("Barak", "Obama"), //
		new MyBean("George", "Bush"), //
		new MyBean("Bill", "Clinton") //
	);
	
	@Override
	public MyBean getRowData(String rowKey) {
		return this.data.get(Integer.parseInt(rowKey));
	}
	
	@Override
	public Object getRowKey(MyBean object) {
		return this.data.indexOf(object);
	}

	@Override
	public List<MyBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<MyBean> result = sorted(data.stream(), sortField, sortOrder).skip(first).limit(pageSize).collect(toList());
		setRowCount(result.size());
		return result;
	}
	
	private Stream<MyBean> sorted(Stream<MyBean> stream, String sortField, SortOrder sortOrder) {
		return sortField != null && sortOrder != SortOrder.UNSORTED ? stream.sorted(toComparator(sortField, sortOrder)) : stream;
	}

	private Comparator<MyBean> toComparator(String sortField, SortOrder sortOrder) {
		switch (sortField) {
		case "firstName":
			return withSortOrder(sortOrder, Comparator.comparing(MyBean::getFirstName));
		case "lastName":
			return withSortOrder(sortOrder, Comparator.comparing(MyBean::getLastName));
		default:
			throw new IllegalArgumentException("Unknown sort field " + sortField);
		}
	}
	
	private <T> Comparator<T> withSortOrder(SortOrder sortOrder, Comparator<T> comparator) {
		switch (sortOrder) {
		case ASCENDING:
			return comparator;
		case DESCENDING:
			return comparator.reversed();
		default:
			throw new IllegalArgumentException("Unknown sort order " + sortOrder);
		}
	}
}
