import argparse
import sys, os, pprint
import typing
import xml.etree.ElementTree as ET


class TestCase:

    def __init__(self, name: str, is_passed: bool) -> None:
        self.name = str(name)
        self.is_passed = bool(is_passed)

    def toString(self, indent: int = 0):
        return "\t" * indent + f"{self.name} -> {'PASSED' if self.is_passed else 'FAILURE'}"

    def __bool__(self):
        return self.is_passed


class ParametrisedTestCase(TestCase):

    def __init__(self, name: str, cases: list[TestCase]) -> None:
        super().__init__(name, all(cases))
        self.cases = cases

    def add_case(self, case: TestCase):
        self.is_passed = self.is_passed and case.is_passed
        self.cases.append(case)

    def toString(self, indent: int = 0):
        inline_cases = []
        for case in self.cases:
            inline_cases.append(case.toString(indent + 1))

        inline_cases = '\n'.join(inline_cases).rstrip()
        return "\t" * indent + f"{self.name} -> {'PASSED' if self.is_passed else 'FAILURE'}\n{inline_cases}"


def parse_args(args: list[str]) -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        prog="Test Result Printer",
        description="Program read xml files with tests results and print it at terminal at stdin",
    )
    parser.add_argument(
        "-d", "--dir", required=True,
        help="setup path to directory with  xml files with tests information",
        metavar="<?>"
    )
    return parser.parse_args(args)


def display_test(test_path: str):
    tree_root = ET.parse(test_path).getroot()
    print(
        "Tests of ",
        tree_root.attrib.get("name", "UncnownTestSuite").split('.')[-1].replace("Test", ":"),
        sep=" "
    )
    cases = dict()
    for child in tree_root:
        if child.tag != "testcase":
            continue

        name = child.get("name", "uncknown test cases")
        is_passed = child.find("failure") is None
        if '[' in name:
            primary_name = name.split('[')[0]
            args = '[' + "[".join(name.split('[')[1:])

            case: ParametrisedTestCase = cases.get(primary_name, ParametrisedTestCase(primary_name, []))
            case.add_case(TestCase(args, is_passed))
            cases[primary_name] = case
        else:
            cases[name] = TestCase(name, is_passed)

    for name in sorted(cases.keys()):
        print(cases[name].toString(indent=1))

    passed_test_count = int(tree_root.attrib.get("tests", 0)) - int(tree_root.attrib.get("failures", 0))
    print(
        f"Passed: {passed_test_count}",
        f"Failures: {tree_root.attrib.get('failures', 0)}",
        f"Time: {tree_root.attrib.get('time', 0.0)}",
        sep=" ",
        end=os.linesep * 2
    )
    return []


if __name__ == "__main__":
    ns = parse_args(sys.argv[1:])

    tests_result_dir = getattr(ns, "dir")
    childs = os.listdir(tests_result_dir)
    for child in sorted(childs):
        child_path = os.path.join(tests_result_dir, child)
        if not os.path.isfile(child_path):
            continue

        if not (child.startswith("TEST") and child.endswith(".xml")):
            continue

        try:
            display_test(child_path)
        except Exception as e:
            print(f"Can't display ttest information at file '{child}': {e}", file=sys.stderr)
